package de.bhelfert.rootyphp.amortizationtable

import de.bhelfert.rootyphp.amortizationtable.cli.CommandLineParser
import de.bhelfert.rootyphp.amortizationtable.presentation.AmortizationTableConsolePrinter
import de.bhelfert.rootyphp.amortizationtable.model.AmortizationTable
import groovy.transform.ToString

@ToString(includeFields = true, includeNames = true)
class AmortizationTableCalculator {

    private CommandLineParser commandLineParser = new CommandLineParser()
    private AmortizationTable amortizationTable = new AmortizationTable()
    private AmortizationTableConsolePrinter amortizationTableConsolePrinter = new AmortizationTableConsolePrinter()

    static main(args) {
        new AmortizationTableCalculator().calculateAndPrintAmortizationTable(args)
    }

    void calculateAndPrintAmortizationTable(String[] args) {
        def parameters = commandLineParser.parseCommandLine(args)
        if (!parameters) {
            return
        }

        def tableLines = amortizationTable.calculateTableLines(parameters)
        amortizationTableConsolePrinter.printAmortizationTable(tableLines)
    }
}
