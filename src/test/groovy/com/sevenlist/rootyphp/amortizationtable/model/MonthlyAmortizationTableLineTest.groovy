package com.sevenlist.rootyphp.amortizationtable.model

import com.sevenlist.rootyphp.amortizationtable.util.DateUtil
import spock.lang.Specification

import static TestData.*

class MonthlyAmortizationTableLineTest extends Specification {

    DateUtil dateUtil = new DateUtil()

    MonthlyAmortizationTableLine line = new MonthlyAmortizationTableLine(PAYOUT_LINE)

    def "references the same parameters object as the previous line"() {
        expect:
        line.parameters.is(PAYOUT_LINE.parameters)
    }

    def "has a date that is the end of the next month of the previous line date"() {
        given:
        def endOfNextMonthDate = dateUtil.getEndOfMonth(PAYOUT_LINE.date.plusMonths(1))

        expect:
        line.date == endOfNextMonthDate
    }

    def "has a residual debt that is the sum of the previous line's residual debt and the amortization"() {
        expect:
        line.residualDebt == -99833.33333
    }

    def "has an interest that is the negated result of the multiplication of the previous residual debt and the underperiodic interest"() {
        expect:
        line.interest == 131.66667
    }

    def "has an amortization that is the interest subtracted from the rate"() {
        expect:
        line.amortization == 166.66667
    }

    def "has an rate that is ((((debit interest / 100) + (initial amortization / 100)) / 12) * loan amount"() {
        expect:
        line.rate == 298.33334
    }
}
