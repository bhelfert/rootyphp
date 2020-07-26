package de.bhelfert.rootyphp.amortizationtable.model

import spock.lang.Specification

import static TestData.*
import static java.math.BigDecimal.ROUND_HALF_UP

class AmortizationTableTest extends Specification {

    AmortizationTable table = new AmortizationTable()

    def "has the correct number of table lines"() {
        when:
        List tableLines = table.calculateTableLines(PARAMETERS)

        then:
        tableLines.size() == 121 // = payout line + (12 month per year * 10 years)
    }

    def "its first line is a payout amortization table line with the correct values"() {
        when:
        List tableLines = table.calculateTableLines(PARAMETERS)

        then:
        tableLines.first() == PAYOUT_LINE
    }

    def "has 120 monthly amortization table lines"() {
        when:
        List tableLines = table.calculateTableLines(PARAMETERS)

        then:
        tableLines.count { it instanceof MonthlyAmortizationTableLine } == 120
    }

    def "its last line contains the correct values"() {
        when:
        List tableLines = table.calculateTableLines(PARAMETERS)

        and:
        MonthlyAmortizationTableLine lastLine = tableLines.last()

        then:
        lastLine.date == PAYOUT_LINE.date.plusYears(PARAMETERS.fixedInterestRateInYears)
        lastLine.residualDebt.setScale(2, ROUND_HALF_UP) == -78348.80
        lastLine.interest.setScale(2, ROUND_HALF_UP) == 103.42
        lastLine.amortization.setScale(2, ROUND_HALF_UP) == 194.92
        lastLine.rate.setScale(2, ROUND_HALF_UP) == 298.33
    }
}
