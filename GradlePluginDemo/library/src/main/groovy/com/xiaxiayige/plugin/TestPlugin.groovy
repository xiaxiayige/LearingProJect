package com.xiaxiayige.plugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {

    void apply(Project project) {
        println("==============================> this is TestPlugin ")
    }
}