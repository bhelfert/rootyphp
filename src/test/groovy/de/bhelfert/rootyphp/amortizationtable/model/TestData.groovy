package de.bhelfert.rootyphp.amortizationtable.model

import java.time.LocalDate

class TestData {

    static final AmortizationTableParameters PARAMETERS = [
            loanAmountInEuro         : 100000,
            debitInterestFactor      : 1.58,
            initialAmortizationFactor: 2,
            fixedInterestRateInYears : 10
    ]

    static final PayoutAmortizationTableLine PAYOUT_LINE = new PayoutAmortizationTableLine(PARAMETERS)

    static final LocalDate DATE_OF_LINE_ONE = LocalDate.of(2015, 8, 1)
    static final LocalDate DATE_OF_LINE_TWO = LocalDate.of(2015, 9, 1)

    static final BigDecimal RESIDUAL_DEBT_OF_LINE_ONE = 100000
    static final BigDecimal RESIDUAL_DEBT_OF_LINE_TWO = 90000

    static final BigDecimal INTEREST_OF_LINE_ONE = 200
    static final BigDecimal INTEREST_OF_LINE_TWO = 100

    static final BigDecimal AMORTIZATION_OF_LINE_ONE = 300
    static final BigDecimal AMORTIZATION_OF_LINE_TWO = 600

    static final BigDecimal RATE_OF_LINE_ONE = 500
    static final BigDecimal RATE_OF_LINE_TWO = 700

    static final SomeAmortizationTableLine LINE_ONE = [
            date        : DATE_OF_LINE_ONE,
            residualDebt: RESIDUAL_DEBT_OF_LINE_ONE,
            interest    : INTEREST_OF_LINE_ONE,
            amortization: AMORTIZATION_OF_LINE_ONE,
            rate        : RATE_OF_LINE_ONE
    ]

    static final SomeAmortizationTableLine LINE_TWO = [
            date        : DATE_OF_LINE_TWO,
            residualDebt: RESIDUAL_DEBT_OF_LINE_TWO,
            interest    : INTEREST_OF_LINE_TWO,
            amortization: AMORTIZATION_OF_LINE_TWO,
            rate        : RATE_OF_LINE_TWO
    ]

    static final List TWO_AMORTIZATION_TABLE_LINES = [LINE_ONE, LINE_TWO]

    static class SomeAmortizationTableLine extends AmortizationTableLine {

        @Override
        protected LocalDate calculateDate() {
            throw new IllegalStateException('date has to be set as named parameter')
        }

        @Override
        protected BigDecimal calculateResidualDebt() {
            throw new IllegalStateException('residualDebt has to be set as named parameter')
        }

        @Override
        protected BigDecimal calculateInterest() {
            throw new IllegalStateException('interest has to be set as named parameter')
        }

        @Override
        protected BigDecimal calculateAmortization() {
            throw new IllegalStateException('amortization has to be set as named parameter')
        }

        @Override
        protected BigDecimal calculateRate() {
            throw new IllegalStateException('rate has to be set as named parameter')
        }
    }

    private TestData() {
    }
}
