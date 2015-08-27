package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.ToString

@ToString(includeFields = true, includeNames = true)
class AmortizationTable {

    private static final int TWELVE_MONTHS_PER_YEAR = 12

    private AmortizationTableParameters parameters
    private List amortizationTableLines

    List calculateTableLines(AmortizationTableParameters parameters) {
        this.parameters = parameters
        amortizationTableLines = []

        addFirstTableLine()
        addRemainingTableLines()
        amortizationTableLines.last().calculateEntries()

        Collections.unmodifiableList(amortizationTableLines)
    }

    private void addFirstTableLine() {
        amortizationTableLines << new PayoutAmortizationTableLine(parameters)
    }

    private void addRemainingTableLines() {
        int lineCount = parameters.fixedInterestRateInYears * TWELVE_MONTHS_PER_YEAR
        lineCount.times {
            def previousLine = amortizationTableLines.last()
            amortizationTableLines << new MonthlyAmortizationTableLine(previousLine)
        }
    }
}
