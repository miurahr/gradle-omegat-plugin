package org.omegat.gradle

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.tasks.Delete


class OmegatPlugin implements Plugin<Project> {
    static final String OMEGAT_CONFIGURATION_NAME = "omegatPlugin"
    static final String TASK_BUILD_NAME = "translate"
    static final String TASK_CLEAN_NAME = 'cleanTranslation'
    private Project project

    private getRootDirectory() {
        String omtRoot = project.omegat.projectDir
        if (omtRoot.equals("")) {
            omtRoot = project.getRootDir().toString()
        }
        return omtRoot
    }

    private getTargetDirectory() {
        String omtRoot = project.omegat.projectDir
        if (omtRoot.equals("")) {
            omtRoot = project.getRootDir().toString()
        }
        def records=new XmlSlurper().parse(new File(omtRoot, 'omegat.project'))
        String targetDir = records.'omegat'[0].'target_dir'[0]
        if (targetDir == "__DEFAULT__") {
            targetDir = "target"
        }
        return targetDir
    }

    private isProjectTranslation() {
        def omtProject = new File(getRootDirectory(), 'omegat.project')
        return omtProject.exists()
    }

    @Override
    void apply(Project project) {
        this.project = project

        def extension = project.extensions.create("omegat", OmegatPluginExtension)

        Configuration config = project.configurations.create(OMEGAT_CONFIGURATION_NAME)
                .setVisible(false).setTransitive(true)
                .setDescription('The OmegaT configuration for this project.')

        project.with {
            if (isProjectTranslation()) {
                tasks.create(name: TASK_BUILD_NAME, type: OmegatTask) {
                    description = "Generate translations into OmegaT target directory."
                    options = [getRootDirectory(), "--mode=console-translate"]
                }

                tasks.create(name: TASK_CLEAN_NAME, type: Delete) {
                    description = 'Clean OmegaT target directory.'
                    new File(getRootDirectory(), getTargetDirectory()).listFiles()
                            .findAll { it.isDirectory() || !(it.name.startsWith('.')) }.each {
                        delete it
                    }
                }
            }

            afterEvaluate {
                Properties props = new Properties()
                props.load(OmegatPlugin.class.getResourceAsStream("omegat.properties"))
                project.repositories.jcenter()
                project.repositories.maven(new Action<MavenArtifactRepository>() {
                    @Override
                    void execute(MavenArtifactRepository mavenArtifactRepository) {
                        mavenArtifactRepository.setUrl(props.getProperty("mavenRepositoryUrl"))
                    }
                })
                def group = props.getProperty("omegatGroup")
                def artifact = props.getProperty("omegatArtifact")
                def version = extension.version ?: props.getProperty("omegatVersion")
                def dep = "${group}:${artifact}:${version}"
                if (isProjectTranslation()) {
                    project.dependencies.add(OMEGAT_CONFIGURATION_NAME, dep)
                } else {
                    project.dependencies.add('compileOnly', dep)
                    project.dependencies.add('testImplementation', dep)
                }
            }
        }
    }
}
