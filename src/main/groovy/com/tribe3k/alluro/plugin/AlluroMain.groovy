package com.tribe3k.alluro.plugin

import com.tribe3k.alluro.service.ContentParser
import com.tribe3k.alluro.service.PlantUMLMarkdownGenerator
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction


class AlluroMain extends DefaultTask {
    String gradleDependenciesOutput
    String desiredContext = 'compile'

    @TaskAction
    void convertOutputToPlantUml() {
        gradleDependenciesOutput = project.file('dependencies.txt').text

        def rootContext = ContentParser.readGradleDependenciesOutput(gradleDependenciesOutput, desiredContext)
        def results = PlantUMLMarkdownGenerator.generateDependencyRelationship(rootContext)
        println results
    }

    void setGradleDependenciesOutput(gradleDependenciesOutput) {
        this.gradleDependenciesOutput = gradleDependenciesOutput
    }

    String getGradleDependenciesOutput() {
        return gradleDependenciesOutput
    }

    void setDesiredContext(context) {
        this.desiredContext = context
    }
}
