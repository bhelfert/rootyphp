package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeSuper = true)
class YearlyAmortizationTableLine extends SummatingAmortizationTableLine {

    YearlyAmortizationTableLine(List monthlyAmortizationTableLines) {
        super(monthlyAmortizationTableLines)
    }

    @Override
    LocalDate getDate() {
        if (!super.@date) {
            date = amortizationTableLinesToSumUp.first().date
        }
        super.@date
    }
}
