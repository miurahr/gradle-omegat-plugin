package org.omegat.gradle.task

import org.gradle.api.tasks.CacheableTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction


@CacheableTask
open class BaseTask : JavaExec() {
    init {
        group = "org.omegat"
        main = "org.omegat.Main"
    }

    @Input
    var projectDir: String = project.rootDir.toString()

    protected fun getArgList(): MutableList<String> {
        val argList: MutableList<String> = mutableListOf(projectDir)
        if (hasProperty("user.language")) {
            argList.add("-Duser.language=" + property("user.language"))
            if (hasProperty("user.country")) {
                argList.add("-Duser.country=" + property("user.country"))
            }
        }
        if (hasProperty("http.proxyHost")) {
            argList.add("-Dhttp.proxyHost=" + property("http.proxyHost"))
        }
        argList.add("--disable-location-save")
        return argList
    }

    @TaskAction
    override fun exec() {
        args = getArgList()
        maxHeapSize = "2048M"
        classpath = project.configurations.getByName("omegat")
        super.exec()
    }
}