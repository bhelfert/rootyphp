package de.bhelfert.rootyphp.amortizationtable.util

import spock.lang.Specification

import java.time.LocalDate

class DateUtilTest extends Specification {

    DateUtil dateUtil = new DateUtil()

    def "calculates end of month date"() {
        when:
        def endOfMonthDate = dateUtil.getEndOfMonth(LocalDate.of(2015, 8, 1))

        then:
        endOfMonthDate == LocalDate.of(2015, 8, 31)
    }
}
