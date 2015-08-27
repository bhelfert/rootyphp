package com.sevenlist.rootyphp.amortizationtable

import com.sevenlist.rootyphp.amortizationtable.cli.CommandLineParser
import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTable
import com.sevenlist.rootyphp.amortizationtable.presentation.AmortizationTableConsolePrinter
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