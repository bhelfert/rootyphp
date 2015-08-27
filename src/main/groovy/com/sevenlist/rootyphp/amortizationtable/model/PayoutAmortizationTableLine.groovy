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
        calculateEntries()
    }

    /**
     * Sets the date to the end of the current month.
     * Sets the residual debt, the payout, and the rate to the negative loan amount.
     * Sets the interest to zero.
     */
    private void calculateEntries() {
        date = getEndOfMonth(LocalDate.now())
        residualDebt = amortization = rate = loanAmountInEuro.negate()
        interest = 0
    }
}
