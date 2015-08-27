package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeSuper = true, includeFields = true, includeNames = true)
class MonthlyAmortizationTableLine extends AmortizationTableLine {

    private static final int TWELVE_MONTHS_PER_YEAR = 12

    private LocalDate previousDate
    private BigDecimal previousResidualDebt
    private BigDecimal underperiodicInterest

    MonthlyAmortizationTableLine(AmortizationTableLine previousLine) {
        super(previousLine.parameters)

        previousDate = previousLine.date

        // As all values are calculated lazily, accessing residualDebt results in
        // the calculation of all the _previous_ object's properties.
        previousResidualDebt = previousLine.residualDebt
    }

    void calculateEntries() {
        residualDebt
    }

    LocalDate getDate() {
        if (!super.@date) {
            date = calculateDate()
        }
        super.@date
    }

    private LocalDate calculateDate() {
        def nextMonthDate = previousDate.plusMonths(1)
        getEndOfMonth(nextMonthDate)
    }

    BigDecimal getResidualDebt() {
        if (!super.@residualDebt) {
            residualDebt = calculateResidualDebt()
        }
        super.@residualDebt
    }

    /**
     * residual debt = (previous residual debt + amortization)
     */
    private BigDecimal calculateResidualDebt() {
        previousResidualDebt + amortization
    }

    BigDecimal getInterest() {
        if (!super.@interest) {
            interest = calculateInterest()
        }
        super.@interest
    }

    /**
     * interest = -(previous residual debt * underperiodic interest)
     */
    private BigDecimal calculateInterest() {
        (previousResidualDebt * getUnderperiodicInterest()).negate()
    }

    BigDecimal getAmortization() {
        if (!super.@amortization) {
            amortization = calculateAmortization()
        }
        super.@amortization
    }

    /**
     * amortization = (rate - interest)
     */
    private BigDecimal calculateAmortization() {
        rate - interest
    }

    BigDecimal getRate() {
        if (!super.@rate) {
            rate = calculateRate()
        }
        super.@rate
    }

    /**
     * rate = ((((debit interest / 100) + (initial amortization / 100)) / 12) * loan amount)
     */
    private BigDecimal calculateRate() {
        def underperiodicInitialAmortization = calculateUnderperiodicValue(initialAmortizationFactor)
        rate = (getUnderperiodicInterest() + underperiodicInitialAmortization) * loanAmountInEuro
    }

    BigDecimal getUnderperiodicInterest() {
        if (!this.@underperiodicInterest) {
            underperiodicInterest = calculateUnderperiodicInterest()
        }
        this.@underperiodicInterest
    }

    /**
     * underperiodic interest = ((debit interest / 100) / 12)
     */
    private BigDecimal calculateUnderperiodicInterest() {
        calculateUnderperiodicValue(debitInterestFactor)
    }

    /**
     * underperiodic value = (value / 12)
     */
    private BigDecimal calculateUnderperiodicValue(BigDecimal value) {
        value / TWELVE_MONTHS_PER_YEAR
    }
}
