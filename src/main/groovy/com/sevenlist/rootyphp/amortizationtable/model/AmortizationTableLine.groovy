package com.sevenlist.rootyphp.amortizationtable.model

import com.sevenlist.rootyphp.amortizationtable.util.DateUtil
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import java.time.LocalDate

@TupleConstructor(includeFields = true, includes = 'parameters')
@EqualsAndHashCode
@ToString(includeFields = true, includes = 'date, residualDebt, interest, amortization, rate', includeNames = true)
abstract class AmortizationTableLine {

    @Delegate
    private static final DateUtil DATE_UTIL = new DateUtil()

    @Delegate
    AmortizationTableParameters parameters

    LocalDate date
    BigDecimal residualDebt
    BigDecimal interest
    BigDecimal amortization
    BigDecimal rate
}
