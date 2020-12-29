package org.omegat.gradle

import org.gradle.api.Action
import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.repositories.MavenArtifactRepository
import org.gradle.api.tasks.Delete
import org.gradle.jvm.tasks.Jar


class OmegatPlugin implements Plugin<Project> {
    static final String CONFIGURATION_NAME = "omegat"
    static final String EXTENSION_NAME = "omegat"
    static final String FATJAR_CONF_NAME = "packIntoJar"
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

    private isProjectPlugin() {
        return !isProjectTranslation()
    }

    private isProjectTranslation() {
        def omtProject = new File(getRootDirectory(), 'omegat.project')
        return omtProject.exists()
    }

    @Override
    void apply(Project project) {
        this.project = project
        def extension = project.extensions.create(EXTENSION_NAME, OmegatPluginExtension)
        def config = project.configurations.create(CONFIGURATION_NAME)
        config.setVisible(false).setTransitive(true).setDescription('The OmegaT configuration for this project.')
        if (isProjectPlugin()) {
            def omtPluginConfig = project.configurations.create(FATJAR_CONF_NAME)
            omtPluginConfig.setVisible(true).setTransitive(true).setDescription('The OmegaT Plugin configuration for FatJar generation')
       }

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
                def omegatVersion = extension.version ?: props.getProperty("omegatVersion")
                def vldockingVersion = props.getProperty("vldockingVersion")
                def deps = ["org.omegat:omegat:${omegatVersion}", "org.omegat:vldocking:3.0.5"]
                for (String dep: deps) {
                    project.dependencies.add(CONFIGURATION_NAME, dep)
                }
                if (isProjectPlugin()) {
                    def jarTask = project.tasks.withType(Jar.class).getByName("jar")
                    jarTask.outputs.upToDateWhen { false }
                    jarTask.doFirst { task ->
                        jarTask.manifest.attributes([ "OmegaT-Plugins" : project.extensions.omegat.pluginClass])
                        jarTask.from(
                                task.project.configurations.getByName(FATJAR_CONF_NAME).files.collect({file ->
                                    file.isDirectory() ? project.fileTree(file) : project.zipTree(file)
                                })
                        )
                    }
                    def implementation = project.configurations.implementation
                    def omtPluginConfig = project.configurations.getByName(FATJAR_CONF_NAME)
                    implementation.extendsFrom(omtPluginConfig)
                    Configuration testImplementation = project.configurations.testImplementation
                    for (String dep: deps) {
                        implementation.dependencies.add(project.dependencies.create(dep))
                        testImplementation.dependencies.add(project.dependencies.create(dep))
                    }
                }
            }
        }
    }
}
