package com.sevenlist.rootyphp.amortizationtable.presentation

import groovy.transform.InheritConstructors
import groovy.transform.ToString

import java.time.LocalDate

@InheritConstructors
@ToString(includeSuper = true)
class YearlyAmortizationTableLine extends SummatingAmortizationTableLine {

    @Override
    protected LocalDate calculateDate() {
        amortizationTableLinesToSumUp.first().date
    }
}
