package de.bhelfert.rootyphp.amortizationtable.presentation

import spock.lang.Specification

import static de.bhelfert.rootyphp.amortizationtable.model.TestData.*

class EndOfFixedInterestRateAmortizationTableLineTest extends Specification {

    def payoutAndTwoMonthlyTableLines = [PAYOUT_LINE] + TWO_AMORTIZATION_TABLE_LINES
    EndOfFixedInterestRateAmortizationTableLine line = new EndOfFixedInterestRateAmortizationTableLine(payoutAndTwoMonthlyTableLines)

    def "has no date"() {
        expect:
        !line.date
    }

    def "has residual debt of last amortization table line"() {
        expect:
        line.residualDebt == RESIDUAL_DEBT_OF_LINE_TWO
    }

    def "sums up interests without payout table line"() {
        expect:
        line.interest == INTEREST_OF_LINE_ONE + INTEREST_OF_LINE_TWO
    }

    def "sums up amortizations without payout table line"() {
        expect:
        line.amortization == AMORTIZATION_OF_LINE_ONE + AMORTIZATION_OF_LINE_TWO
    }

    def "sums up rates without payout table line"() {
        expect:
        line.rate == RATE_OF_LINE_ONE + RATE_OF_LINE_TWO
    }
}
