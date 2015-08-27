package com.sevenlist.rootyphp.amortizationtable.model

import spock.lang.Specification

import static TestData.*

class EndOfFixedInterestRateAmortizationTableLineTest extends Specification {

    EndOfFixedInterestRateAmortizationTableLine line = new EndOfFixedInterestRateAmortizationTableLine(TWO_AMORTIZATION_TABLE_LINES)

    def "contains all but the first amortization table lines"() {
        expect:
        // if it would also wrongly contain the first line the interest would be the sum of INTEREST_OF_LINE_ONE and INTEREST_OF_LINE_TWO
        line.interest == INTEREST_OF_LINE_TWO
    }

    def "has no date"() {
        expect:
        !line.date
    }
}
