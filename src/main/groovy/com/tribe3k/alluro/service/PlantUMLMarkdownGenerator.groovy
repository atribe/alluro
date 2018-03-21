package com.tribe3k.alluro.service

import com.tribe3k.alluro.models.NestedDependency
import com.tribe3k.alluro.models.RootContext


class PlantUMLMarkdownGenerator {
    private static String relationshipMarkdown = " <|-- "

    static String  generateDependencyRelationship(final RootContext context) {
        def parentContextName = context.getName()
        def plantUMLBuilder = new StringBuilder()

        for (NestedDependency nestedDependency : context.getDependencies()) {
            if (!nestedDependency.getDependencies().isEmpty()) {
                outputLevel(plantUMLBuilder, nestedDependency, parentContextName)
            } else {
                plantUMLBuilder.append(parentContextName).append(".").append(nestedDependency.getGroupAndName())
                plantUMLBuilder.append(System.lineSeparator())
            }
        }

        return plantUMLBuilder.toString()
    }

    private static void outputLevel(final StringBuilder stringBuilder, final NestedDependency parentDependency, String parentContextName) {

        for (NestedDependency nestedDependency : parentDependency.getDependencies()) {
            stringBuilder
                    .append(parentContextName)
                    .append(".")
                    .append(parentDependency.getGroupAndName())
                    .append(relationshipMarkdown)
                    .append(parentContextName)
                    .append(".")
                    .append(nestedDependency.getGroupAndName())
            stringBuilder.append(System.lineSeparator())

            if (!nestedDependency.getDependencies().isEmpty()) {
                outputLevel(stringBuilder, nestedDependency, parentContextName)
            }
        }
    }
}