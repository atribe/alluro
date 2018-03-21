package com.tribe3k.alluro.service


class ContentParserTest extends GroovyTestCase {
    def expectedResults = 'compile.com.fasterxml.jackson.core.jackson_databind <|-- compile.com.fasterxml.jackson.core.jackson_annotations\r\n' +
            'compile.com.fasterxml.jackson.core.jackson_databind <|-- compile.com.fasterxml.jackson.core.jackson_core\r\n'

    void testReadGradleDependenciesOutput() {
        def fileContents = this.getClass().getClassLoader().getResource( 'dependencies.txt' ).text

        def rootContext = ContentParser.readGradleDependenciesOutput(fileContents,'compile')
        def results = PlantUMLMarkdownGenerator.generateDependencyRelationship(rootContext)
        assertEquals(expectedResults, results)
    }
}
