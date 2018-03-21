package com.tribe3k.alluro.service

import com.tribe3k.alluro.models.NestedDependency
import com.tribe3k.alluro.models.RootContext

import java.util.regex.Matcher
import java.util.regex.Pattern
import java.util.stream.Collectors

class ContentParser {
    private static Pattern pattern = Pattern.compile("^ *((\\| +)*(\\+-+|(\\| +)*\\\\-+)) ([\\w.-]*):([\\w-]*)");

    static RootContext readGradleDependenciesOutput(String rawDependenciesOutput, String desiredContext) {
        def lines = trimFileContents(rawDependenciesOutput, desiredContext)

        def root = new RootContext(extractContextTitle(lines[0]))

        if(lines[1] == "No dependencies") {
            return root
        }

        def rawDependencyList = Arrays.asList(lines.drop(1))

        def dependenciesList = convertStringsToNestedDependencies(rawDependencyList)

        Stack<NestedDependency> dependencyStack = convertListToStack(dependenciesList)

        LevelParser.parseLevel(root, dependencyStack)

        return root
    }

    static String extractContextTitle(String s) {
        s.take(s.indexOf(" - "))
    }

    private static String[] trimFileContents(fileContents, desiredContext) {
        def lines = fileContents.split(System.lineSeparator())

        def startIndex = lines.findIndexOf { it =~ /^${desiredContext}.*/ }

        def startTrimmedLines = lines.drop(startIndex)

        def endIndex = startTrimmedLines.findIndexOf { it.size() == 0 }

        return startTrimmedLines.take(endIndex)
    }

    private static List<NestedDependency> convertStringsToNestedDependencies(dependencyList) {
        def mapLineToNestedDependency = { dependencyLine ->
            Matcher matcher = pattern.matcher(dependencyLine)
            matcher.find()

            return new NestedDependency(
                    calcDependencyDepth(dependencyLine),
                    matcher.group(3),
                    matcher.group(5),
                    matcher.group(6))
        }


        return dependencyList
                .stream()
                .map(mapLineToNestedDependency)
                .collect(Collectors.toList())
    }

    private static int calcDependencyDepth(dependencyLine) {
        if (dependencyLine.contains("+")) {
            return dependencyLine.indexOf("+") / 5
        } else {
            return dependencyLine.indexOf("\\") / 5
        }
    }

    private static Stack<NestedDependency> convertListToStack(dependencyList) {
        def dependencyStack = new Stack<>()
        dependencyStack.addAll(dependencyList.reverse())
        return dependencyStack
    }
}
