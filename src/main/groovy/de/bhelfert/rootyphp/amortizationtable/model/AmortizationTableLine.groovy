package de.bhelfert.rootyphp.amortizationtable.model


import de.bhelfert.rootyphp.amortizationtable.util.DateUtil
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

import java.time.LocalDate

@TupleConstructor(includes = 'parameters')
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

    /**
     * Both @EqualsAndHashCode and @ToString are not yet working correctly for @Lazy properties so
     * we implement it ourselves in a slightly different way.
     */
    @Override
    Object getProperty(String property) {
        if (!getMetaClass().getProperty(this, property)) {
            def calculatedValue = "calculate${property.capitalize()}"()
            setProperty(property, calculatedValue)
        }
        getMetaClass().getProperty(this, property)
    }

    protected abstract LocalDate calculateDate()

    protected abstract BigDecimal calculateResidualDebt()

    protected abstract BigDecimal calculateInterest()

    protected abstract BigDecimal calculateAmortization()

    protected abstract BigDecimal calculateRate()
}
