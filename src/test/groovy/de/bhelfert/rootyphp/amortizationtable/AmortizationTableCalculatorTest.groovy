package de.bhelfert.rootyphp.amortizationtable

import de.bhelfert.rootyphp.amortizationtable.cli.CommandLineParser
import de.bhelfert.rootyphp.amortizationtable.model.AmortizationTable
import de.bhelfert.rootyphp.amortizationtable.model.AmortizationTableParameters
import de.bhelfert.rootyphp.amortizationtable.presentation.AmortizationTableConsolePrinter
import spock.lang.Specification

class AmortizationTableCalculatorTest extends Specification {

    CommandLineParser commandLineParserMock = Mock()
    AmortizationTable amortizationTableMock = Mock()
    AmortizationTableConsolePrinter amortizationTableConsolePrinterMock = Mock()

    AmortizationTableCalculator amortizationTableCalculator = new AmortizationTableCalculator([
            commandLineParser: commandLineParserMock,
            amortizationTable: amortizationTableMock,
            amortizationTableConsolePrinter: amortizationTableConsolePrinterMock
    ])

    def "program exits when a wrong command line option has been used"() {
        given:
        String[] wrongOptionArgs = ['-wrongOption']

        when:
        amortizationTableCalculator.calculateAndPrintAmortizationTable(wrongOptionArgs)

        then:
        1 * commandLineParserMock.parseCommandLine(wrongOptionArgs) >> null
        0 * amortizationTableMock.calculateTableLines(_)
    }

    def "amortization table is calculated and printed when command line options are correct"() {
        given:
        String[] validOptionArgs = ['-validOption']
        def parameters = new AmortizationTableParameters()
        def tableLines = []

        when:
        amortizationTableCalculator.calculateAndPrintAmortizationTable(validOptionArgs)

        then:
        1 * commandLineParserMock.parseCommandLine(validOptionArgs) >> parameters
        1 * amortizationTableMock.calculateTableLines(parameters) >> tableLines
        1 * amortizationTableConsolePrinterMock.printAmortizationTable(tableLines)
    }
}
