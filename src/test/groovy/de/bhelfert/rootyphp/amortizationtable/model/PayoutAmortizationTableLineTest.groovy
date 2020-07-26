package de.bhelfert.rootyphp.amortizationtable.model

import de.bhelfert.rootyphp.amortizationtable.util.DateUtil
import spock.lang.Specification

import java.time.LocalDate

class PayoutAmortizationTableLineTest extends Specification {

    private static final BigDecimal LOAN_AMOUNT = 100000

    AmortizationTableParameters params = [loanAmountInEuro: LOAN_AMOUNT]
    PayoutAmortizationTableLine line = new PayoutAmortizationTableLine(params)

    def "has end of month date"() {
        given:
        def date = LocalDate.now()
        def endOfMonthDate = new DateUtil().getEndOfMonth(date)

        expect:
        line.date == endOfMonthDate
    }

    def "has residual debt of negated loan amount"() {
        expect:
        line.residualDebt == LOAN_AMOUNT.negate()
    }

    def "has amortization of negated loan amount"() {
        expect:
        line.amortization == LOAN_AMOUNT.negate()
    }

    def "has rate of negated loan amount"() {
        expect:
        line.rate == LOAN_AMOUNT.negate()
    }

    def "has interest of value zero"() {
        expect:
        line.interest == 0
    }

}
