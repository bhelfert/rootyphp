package de.bhelfert.rootyphp.amortizationtable.model

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

    void calculateValues() {
        residualDebt
    }

    @Override
    protected LocalDate calculateDate() {
        def nextMonthDate = previousDate.plusMonths(1)
        getEndOfMonth(nextMonthDate)
    }

    /**
     * residual debt = (previous residual debt + amortization)
     */
    @Override
    protected BigDecimal calculateResidualDebt() {
        previousResidualDebt + amortization
    }

    /**
     * interest = -(previous residual debt * underperiodic interest)
     */
    @Override
    protected BigDecimal calculateInterest() {
        (previousResidualDebt * getUnderperiodicInterest()).negate()
    }

    /**
     * amortization = (rate - interest)
     */
    @Override
    protected BigDecimal calculateAmortization() {
        rate - interest
    }

    /**
     * rate = ((((debit interest / 100) + (initial amortization / 100)) / 12) * loan amount)
     */
    @Override
    protected BigDecimal calculateRate() {
        def underperiodicInitialAmortization = calculateUnderperiodicValue(initialAmortizationFactor)
        (getUnderperiodicInterest() + underperiodicInitialAmortization) * loanAmountInEuro
    }

    private BigDecimal getUnderperiodicInterest() {
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
