package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeSuper = true, includeFields = true, includeNames = true)
class YearlyAmortizationTableLine extends AmortizationTableLine {

    private List monthlyAmortizationTableLines

    YearlyAmortizationTableLine(List monthlyAmortizationTableLines) {
        super(null)
        this.monthlyAmortizationTableLines = monthlyAmortizationTableLines
    }

    LocalDate getDate() {
        if (!super.@date) {
            date = monthlyAmortizationTableLines.first().date
        }
        super.@date
    }

    BigDecimal getResidualDebt() {
        if (!super.@residualDebt) {
            residualDebt = monthlyAmortizationTableLines.last().residualDebt
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
        monthlyAmortizationTableLines.sum {
            it."$fieldName"
        }
    }
}
