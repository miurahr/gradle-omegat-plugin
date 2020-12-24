package org.omegat.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec

/**
 * Created by Hiroshi Miura on 08/01/2016.
 */
class OmegatTask extends DefaultTask {
    @Input String[] options

    def main = "org.omegat.Main"
    def heap = "2048M"

    @TaskAction
    void start() {
        project.javaexec({ JavaExecSpec javaExecSpec ->
            javaExecSpec.setMain(main).args(options)
            javaExecSpec.setMaxHeapSize(heap)
            javaExecSpec.setClasspath(project.configurations.getByName(OmegatPlugin.OMEGAT_CONFIGURATION_NAME))
        });
    }

}