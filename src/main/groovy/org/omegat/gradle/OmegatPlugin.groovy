package org.omegat.gradle

import org.gradle.api.Project
import org.gradle.api.Plugin
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.ModuleDependency
import org.gradle.api.tasks.Delete

class OmegatPlugin implements Plugin<Project> {
    static final String OMEGAT_CONFIGURATION_NAME = "omegatPlugin"
    static final String TASK_BUILD_NAME = "translate"
    static final String TASK_CLEAN_NAME = 'cleanTranslation'
    private Project project

    private addDependency(Configuration configuration, String notation,
                                           String exception) {
        ModuleDependency dependency = project.dependencies.create(notation) as ModuleDependency
        dependency.exclude(module: exception)
        configuration.dependencies.add(dependency)
    }

    private addDependency(String configurationName, Object notation) {
        project.dependencies.add(configurationName, notation)
    }

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


    @Override
    def void apply(Project project) {
        this.project = project

        project.extensions.create("omegat", OmegatPluginExtension)

        Configuration config = project.configurations.create(OMEGAT_CONFIGURATION_NAME)
                .setVisible(false).setTransitive(true)
                .setDescription('The OmegaT configuration for this project.')

        project.with {
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

            afterEvaluate {
                if (project.repositories.isEmpty()) {
                    project.repositories.jcenter()
                }

                def configName = OMEGAT_CONFIGURATION_NAME
                addDependency(configName, 'org.omegat.gradle:gradle-omegat-plugin:0.9.0')
                addDependency(configName, 'org.languagetool:languagetool-core:3.3')
                addDependency(configName, 'org.languagetool:hunspell-native-libs:2.9')
                addDependency(configName, 'org.apache.lucene:lucene-analyzers-common:5.2.1')
                addDependency(configName, 'org.apache.lucene:lucene-analyzers-kuromoji:5.2.1')
                addDependency(configName, 'org.apache.lucene:lucene-analyzers-smartcn:5.2.1')
                addDependency(configName, 'org.apache.lucene:lucene-analyzers-stempel:5.2.1')
                addDependency(configName, 'org.eclipse.jgit:org.eclipse.jgit:4.2.0.201601211800-r')
                addDependency(configName, 'com.jcraft:jsch.agentproxy.jsch:0.0.9')
                addDependency(configName, 'com.jcraft:jsch.agentproxy.connector-factory:0.0.9')
                addDependency(configName, 'com.jcraft:jsch.agentproxy.svnkit-trilead-ssh2:0.0.9')
                addDependency(configName, 'org.tmatesoft.svnkit:svnkit:1.8.12')
                addDependency(configName, 'org.apache.pdfbox:pdfbox:2.0.0')
                addDependency(configName, 'net.loomchild:maligna:3.0.0')
                addDependency(configName, 'commons-io:commons-io:2.4')
                addDependency(configName, 'commons-lang:commons-lang:2.6')
                addDependency(configName, 'org.slf4j:slf4j-jdk14:1.7.21')
                addDependency(configName, 'org.dict.zip:dictzip-lib:0.8.1')
                addDependency(configName, 'com.github.takawitter:trie4j:0.9.2')
                addDependency(configName, 'org.madlonkay.supertmxmerge:supertmxmerge:2.0.1')
                addDependency(configName, 'org.omegat:vldocking:3.0.5')
                addDependency(configName, 'org.omegat:juniversalchardet:1.0.4')
                addDependency(configName, 'org.codehaus.groovy:groovy-all:2.4.6')
                addDependency(configName, 'com.fifesoft:rsyntaxtextarea:2.5.8')
                addDependency(configName, 'com.fifesoft:rstaui:2.5.7')
                addDependency(configName, 'com.fifesoft:autocomplete:2.5.8')
                addDependency(config, 'com.fifesoft:languagesupport:2.5.8', 'rhino')
                // Temporary exclude gosen-ipadic
                // see https://sourceforge.net/p/omegat/bugs/814/
                addDependency(config, 'org.languagetool:language-all:3.3', 'lucene-gosen-ipadic')
            }
        }
    }
}
