package com.sevenlist.rootyphp.amortizationtable.presentation

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableLine
import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableParameters
import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeSuper = true, includeFields = true, includeNames = true)
abstract class SummatingAmortizationTableLine extends AmortizationTableLine {

    private static final AmortizationTableParameters NO_PARAMETERS = null

    protected List amortizationTableLinesToSumUp

    SummatingAmortizationTableLine(List amortizationTableLinesToSumUp) {
        super(NO_PARAMETERS)
        this.amortizationTableLinesToSumUp = amortizationTableLinesToSumUp
    }

    abstract LocalDate getDate()

    BigDecimal getResidualDebt() {
        if (!super.@residualDebt) {
            residualDebt = amortizationTableLinesToSumUp.last().residualDebt
        }
        super.@residualDebt
    }

    BigDecimal getInterest() {
        if (!super.@interest) {
            interest = sumOf('interest')
        }
        super.@interest
    }

    BigDecimal getAmortization() {
        if (!super.@amortization) {
            amortization = sumOf('amortization')
        }
        super.@amortization
    }

    BigDecimal getRate() {
        if (!super.@rate) {
            rate = sumOf('rate')
        }
        super.@rate
    }

    private BigDecimal sumOf(String fieldName) {
        amortizationTableLinesToSumUp.sum {
            it."$fieldName"
        }
    }
}
