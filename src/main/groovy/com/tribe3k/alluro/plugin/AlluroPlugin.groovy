package com.tribe3k.alluro.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class AlluroPlugin implements Plugin<Project> {
    void apply(final Project project) {
        def dependencyTask = project.tasks.findByName('dependencies')

        project.tasks.create(name: 'dependencyReportFile', type: dependencyTask.getClass(), { task ->
            task.configure {
                outputFile = project.file('dependencies.txt')
                Set configs = [project.configurations.compile]
                setConfigurations(configs)
            }
        })

        project.tasks.create(name: 'plantUMLGeneration', type: AlluroMain.class, { task ->
            def dependencyReportTask = project.tasks.findByName('dependencyReportFile')
            task.dependsOn(dependencyReportTask)
        })
    }
}
