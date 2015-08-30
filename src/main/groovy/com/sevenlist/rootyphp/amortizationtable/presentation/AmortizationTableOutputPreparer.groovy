package com.sevenlist.rootyphp.amortizationtable.presentation

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableLine
import groovy.transform.ToString

import java.time.LocalDate

@ToString(includeFields = true, includeNames = true)
class AmortizationTableOutputPreparer {

    private static final int NUMBER_OF_YEARS_WITHOUT_YEARLY_AGGREGATION = 3
    private static final int ONE = 1

    private List amortizationTableLines
    private AmortizationTableLine firstTableLine
    private int fixedInterestRate
    private LocalDate dateOfFirstYear
    private LocalDate dateOfSecondYear

    private List yearsToAggregate

    List prepareTableLinesForOutput(List amortizationTableLines) {
        initFields(amortizationTableLines)

        if (isOnlyEndOfFixedInterestRateTableLineToBeAdded()) {
            return getTableLinesWithEndOfFixedInterestTableLine()
        }

        getBothMonthlyAndYearlyTableLines(amortizationTableLines)
    }

    private void initFields(List amortizationTableLines) {
        this.amortizationTableLines = amortizationTableLines
        firstTableLine = amortizationTableLines.first()
        fixedInterestRate = firstTableLine.fixedInterestRateInYears
        dateOfFirstYear = firstTableLine.date
        dateOfSecondYear = dateOfFirstYear.plusYears(ONE)
    }

    private boolean isOnlyEndOfFixedInterestRateTableLineToBeAdded() {
        fixedInterestRate <= NUMBER_OF_YEARS_WITHOUT_YEARLY_AGGREGATION
    }

    private List getTableLinesWithEndOfFixedInterestTableLine() {
        List tableLinesToOutput = []
        tableLinesToOutput.addAll(amortizationTableLines)
        tableLinesToOutput << new EndOfFixedInterestRateAmortizationTableLine(amortizationTableLines)
        tableLinesToOutput
    }

    private List getBothMonthlyAndYearlyTableLines(List amortizationTableLines) {
        List tableLinesToOutput = []
        tableLinesToOutput.addAll(getMonthlyTableLinesForTheFirstTwoYears())
        tableLinesToOutput.addAll(getYearlyTableLinesForInBetween())
        tableLinesToOutput.addAll(getMonthlyTableLinesForLastYear())
        tableLinesToOutput << new EndOfFixedInterestRateAmortizationTableLine(amortizationTableLines)
        tableLinesToOutput
    }

    private List getMonthlyTableLinesForTheFirstTwoYears() {
        List tableLinesToOutput = []
        List firstTwoYears = [dateOfFirstYear.year, dateOfSecondYear.year]
        processTableLinesPerYear(firstTwoYears) {
            tableLinesToOutput.addAll(it)
        }
        tableLinesToOutput
    }

    private List getYearlyTableLinesForInBetween() {
        List tableLinesToOutput = []
        yearsToAggregate = getYearsToAggregate()
        processTableLinesPerYear(yearsToAggregate) {
            tableLinesToOutput << new YearlyAmortizationTableLine(it)
        }
        tableLinesToOutput
    }

    private List getYearsToAggregate() {
        List yearsToAggregate = []
        int numberOfYearsToAggregate = fixedInterestRate - NUMBER_OF_YEARS_WITHOUT_YEARLY_AGGREGATION + ONE
        (1..numberOfYearsToAggregate).each {
            yearsToAggregate << dateOfSecondYear.plusYears(it).year
        }
        yearsToAggregate
    }

    private List getMonthlyTableLinesForLastYear() {
        List tableLinesToOutput = []
        int lastYear = amortizationTableLines.last().date.year
        def tableLinesOfLastYear = findTableLinesOfYear(lastYear)
        tableLinesToOutput.addAll(tableLinesOfLastYear)
        tableLinesToOutput
    }

    private processTableLinesPerYear(List years, Closure c) {
        years.each { year ->
            def tableLinesOfYear = findTableLinesOfYear(year)
            c.call(tableLinesOfYear)
        }
    }

    private List findTableLinesOfYear(int year) {
        amortizationTableLines.findAll { AmortizationTableLine line ->
            line.date.year == year
        }
    }

    boolean hasDateBeenAggregated(LocalDate date) {
        yearsToAggregate.contains(date.year)
    }
}
