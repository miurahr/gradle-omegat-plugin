package org.omegat.gradle.task

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction


@CacheableTask
open class BaseTask : JavaExec() {
    @Input
    protected val argList = mutableListOf<String>()

    @Input
    var projectDir: String? = null

    init {
        group = "org.omegat"
        mainClass.set("org.omegat.Main")
        if (hasProperty("user.language")) {
            argList.add("-Duser.language=" + property("user.language"))
            if (hasProperty("user.country")) {
                argList.add("-Duser.country=" + property("user.country"))
            }
        }
        if (hasProperty("http.proxyHost")) {
            argList.add("-Dhttp.proxyHost=" + property("http.proxyHost"))
        }
    }

    @TaskAction
    override fun exec() {
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        argList.also { args = it }
        super.exec()
    }
}