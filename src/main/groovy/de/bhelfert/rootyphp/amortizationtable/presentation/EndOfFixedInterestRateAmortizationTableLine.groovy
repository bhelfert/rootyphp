package de.bhelfert.rootyphp.amortizationtable.presentation

import groovy.transform.ToString

import java.time.LocalDate

/**
 * Represents the last line of an amortization table, stating the residual debt and
 * all of the other values summed up.
 */
@ToString(includeSuper = true)
class EndOfFixedInterestRateAmortizationTableLine extends SummatingAmortizationTableLine {

    private static final LocalDate NO_DATE = null

    EndOfFixedInterestRateAmortizationTableLine(List amortizationTableLines) {
        super(amortizationTableLines.subList(1, amortizationTableLines.indexOf(amortizationTableLines.last()) + 1))
    }

    @Override
    protected LocalDate calculateDate() {
        NO_DATE
    }
}
