package com.sevenlist.rootyphp.amortizationtable.presentation

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTable
import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableParameters
import com.sevenlist.rootyphp.amortizationtable.model.EndOfFixedInterestRateAmortizationTableLine
import com.sevenlist.rootyphp.amortizationtable.model.MonthlyAmortizationTableLine
import com.sevenlist.rootyphp.amortizationtable.model.PayoutAmortizationTableLine
import com.sevenlist.rootyphp.amortizationtable.model.YearlyAmortizationTableLine
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDate

class AmortizationTableOutputPreparerTest extends Specification {

    private static final int TWELVE_MONTHS_PER_YEAR = 12
    private static final int MONTHS_IN_SECOND_YEAR = TWELVE_MONTHS_PER_YEAR
    private static final int NUMBER_OF_YEARS_OF_MONTHLY_TABLE_LINES = 2

    AmortizationTableOutputPreparer outputPreparer = new AmortizationTableOutputPreparer()

    @Unroll
    def "adds only end of fixed interest rate to output when fixed interest rate (#fixedInterestRate) is up to three years"() {
        given:
        AmortizationTableParameters params = [
                loanAmountInEuro         : 10000,
                debitInterestFactor      : 1,
                initialAmortizationFactor: 2,
                fixedInterestRateInYears : fixedInterestRate
        ]
        def payoutTableLine = new PayoutAmortizationTableLine(params)
        def monthlyTableLine = new MonthlyAmortizationTableLine(payoutTableLine)
        List tableLines = [payoutTableLine, monthlyTableLine]

        when:
        List tableLinesToOutput = outputPreparer.prepareTableLinesForOutput(tableLines)

        then:
        tableLinesToOutput.size() == tableLines.size() + 1
        tableLinesToOutput.containsAll(tableLines)
        tableLinesToOutput.last() instanceof EndOfFixedInterestRateAmortizationTableLine

        where:
        fixedInterestRate << (1..3)
    }

    def "table lines for output have the correct structure when fixed interest rate is greater than three"() {
        given:
        AmortizationTableParameters params = [
                loanAmountInEuro         : 10000,
                debitInterestFactor      : 1,
                initialAmortizationFactor: 2,
                fixedInterestRateInYears : 4
        ]
        List tableLines = new AmortizationTable().calculateTableLines(params)

        when:
        List tableLinesToOutput = outputPreparer.prepareTableLinesForOutput(tableLines)

        then: 'the payout will be stated by the first table line'
        tableLinesToOutput.first() instanceof PayoutAmortizationTableLine

        and: 'followed by monthly table lines until the end of the next year'
        int fromIndexOfInitialMonthlyTableLines = 1
        int numberOfInitialMonthlyTableLines = getNumberOfMonthlyTableLinesWithinTheFirstTwoYears(tableLines)
        int toIndexOfInitialMonthlyTableLines = numberOfInitialMonthlyTableLines
        tableLinesToOutput.subList(fromIndexOfInitialMonthlyTableLines, toIndexOfInitialMonthlyTableLines).each {
            it instanceof MonthlyAmortizationTableLine
        }

        and: 'followed by yearly table lines until the last year starts'
        int fromIndexOfYearlyTableLines = toIndexOfInitialMonthlyTableLines + 1
        int numberOfYearlyTableLines = params.fixedInterestRateInYears - NUMBER_OF_YEARS_OF_MONTHLY_TABLE_LINES
        int toIndexOfYearlyTableLines = fromIndexOfYearlyTableLines + numberOfYearlyTableLines
        tableLinesToOutput.subList(fromIndexOfYearlyTableLines, toIndexOfYearlyTableLines).each {
            it instanceof YearlyAmortizationTableLine
        }

        and: 'followed again by monthly table lines until the end of fixed interest rate has been reached'
        int fromIndexOfRemainingMonthlyTableLines = fromIndexOfYearlyTableLines + 1
        int numberOfRemainingMonthlyTableLines = 2 * TWELVE_MONTHS_PER_YEAR - numberOfInitialMonthlyTableLines
        int toIndexOfRemainingMonthlyTableLines = fromIndexOfRemainingMonthlyTableLines + numberOfRemainingMonthlyTableLines
        tableLinesToOutput.subList(fromIndexOfRemainingMonthlyTableLines, toIndexOfRemainingMonthlyTableLines).each {
            it instanceof MonthlyAmortizationTableLine
        }

        and: 'followed by the summary in form of an end of fixed interest rate table line'
        tableLinesToOutput.last() instanceof EndOfFixedInterestRateAmortizationTableLine
    }

    private int getNumberOfMonthlyTableLinesWithinTheFirstTwoYears(List tableLines) {
        LocalDate dateOfPayout = tableLines.first().date
        int monthOfFirstAmortization = dateOfPayout.plusMonths(1).monthValue
        int numberOfAmortizationsInFirstYear = TWELVE_MONTHS_PER_YEAR - monthOfFirstAmortization + 1
        numberOfAmortizationsInFirstYear + MONTHS_IN_SECOND_YEAR
    }
}
