package com.sevenlist.rootyphp.amortizationtable.presentation

import groovy.transform.ToString

import java.time.LocalDate

/**
 * Represents the last line of an amortization table, stating the residual debt and
 * all of the other values summed up.
 */
@ToString(includeSuper = true)
class EndOfFixedInterestRateAmortizationTableLine extends SummatingAmortizationTableLine {

    EndOfFixedInterestRateAmortizationTableLine(List amortizationTableLines) {
        super(amortizationTableLines.subList(1, amortizationTableLines.indexOf(amortizationTableLines.last()) + 1))
    }

    @Override
    LocalDate getDate() {
        null
    }
}
