package com.sevenlist.rootyphp.amortizationtable.model

import spock.lang.Specification

import static TestData.*

class YearlyAmortizationTableLineTest extends Specification {

    YearlyAmortizationTableLine line = new YearlyAmortizationTableLine(TWO_AMORTIZATION_TABLE_LINES)

    def "has date of first amortization table line"() {
        expect:
        line.date == DATE_OF_LINE_ONE
    }

    def "has residual debt of last amortization table line"() {
        expect:
        line.residualDebt == RESIDUAL_DEBT_OF_LINE_TWO
    }

    def "sums up interests"() {
        expect:
        line.interest == INTEREST_OF_LINE_ONE + INTEREST_OF_LINE_TWO
    }

    def "sums up amortizations"() {
        expect:
        line.amortization == AMORTIZATION_OF_LINE_ONE + AMORTIZATION_OF_LINE_TWO
    }

    def "sums up rates"() {
        expect:
        line.rate == RATE_OF_LINE_ONE + RATE_OF_LINE_TWO
    }
}
