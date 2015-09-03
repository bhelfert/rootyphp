package com.sevenlist.rootyphp.amortizationtable.presentation

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableLine
import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableParameters
import groovy.transform.ToString

@ToString(includeSuper = true, includeFields = true, includeNames = true)
abstract class SummatingAmortizationTableLine extends AmortizationTableLine {

    private static final AmortizationTableParameters NO_PARAMETERS = null

    protected List amortizationTableLinesToSumUp

    SummatingAmortizationTableLine(List amortizationTableLinesToSumUp) {
        super(NO_PARAMETERS)
        this.amortizationTableLinesToSumUp = amortizationTableLinesToSumUp
    }

    @Override
    protected BigDecimal calculateResidualDebt() {
        amortizationTableLinesToSumUp.last().residualDebt
    }

    @Override
    protected BigDecimal calculateInterest() {
        sumOf('interest')
    }

    @Override
    protected BigDecimal calculateAmortization() {
        sumOf('amortization')
    }

    @Override
    protected BigDecimal calculateRate() {
        sumOf('rate')
    }

    private BigDecimal sumOf(String fieldName) {
        amortizationTableLinesToSumUp.sum {
            it."$fieldName"
        }
    }
}
