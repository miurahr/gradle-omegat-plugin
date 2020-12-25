package org.omegat.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction
import org.gradle.process.JavaExecSpec

/**
 * Created by Hiroshi Miura on 08/01/2016.
 */
@CacheableTask
class OmegatTask extends DefaultTask {
    @Input String[] options
    @Input heap = "2048M"

    @TaskAction
    void start() {
        def main = "org.omegat.Main"
        project.javaexec({ JavaExecSpec javaExecSpec ->
            javaExecSpec.setMain(main).args(options)
            javaExecSpec.setMaxHeapSize(heap)
            javaExecSpec.setClasspath(project.configurations.getByName(OmegatPlugin.CONFIGURATION_NAME))
        });
    }

}