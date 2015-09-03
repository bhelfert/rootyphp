package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.ToString

import java.time.LocalDate

/**
 * Represents the first line of an amortization table, stating what is payed out.
 */
@ToString(includeSuper = true)
class PayoutAmortizationTableLine extends AmortizationTableLine {

    PayoutAmortizationTableLine(AmortizationTableParameters parameters) {
        super(parameters)
        calculateValues()
    }

    private void calculateValues() {
        date; residualDebt; interest; amortization; rate
    }

    @Override
    protected LocalDate calculateDate() {
        getEndOfMonth(LocalDate.now())
    }

    @Override
    protected BigDecimal calculateResidualDebt() {
        loanAmountInEuro.negate()
    }

    @Override
    protected BigDecimal calculateInterest() {
        BigDecimal.ZERO
    }

    @Override
    protected BigDecimal calculateAmortization() {
        loanAmountInEuro.negate()
    }

    @Override
    protected BigDecimal calculateRate() {
        loanAmountInEuro.negate()
    }
}
