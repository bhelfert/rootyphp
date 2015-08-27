package com.sevenlist.rootyphp.amortizationtable.cli

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableParameters
import groovy.transform.ToString

import static java.math.RoundingMode.HALF_UP

@ToString(includeFields = true, includeNames = true)
class CommandLineParser {

    private static final BigDecimal ZERO_WITH_SCALE_2 = BigDecimal.ZERO.setScale(2, HALF_UP)

    private static final BigDecimal EXAMPLE_LOAN_AMOUNT = new BigDecimal('100000')
    private static final BigDecimal EXAMPLE_DEBIT_INTEREST = new BigDecimal('1.58')
    private static final BigDecimal EXAMPLE_INITIAL_AMORTIZATION = new BigDecimal('2')
    private static final int EXAMPLE_FIXED_INTEREST_RATE = 10

    private CliBuilder cliBuilder = new CliBuilder(usage: 'amortizationTableCalculator', width: 120)
    private boolean cliDefined = false

    AmortizationTableParameters parseCommandLine(String[] args) {
        if (!cliDefined) {
            defineCommandLineInterface()
        }

        def options = cliBuilder.parse(args)

        if (isArgumentForOptionMissing(options)) {
            // CliBuilder prints usage
            return null
        }

        if (isHelpNeeded(args, options)) {
            cliBuilder.usage()
            return null
        }

        if (shallExampleValuesBeUsed(options)) {
            return new AmortizationTableParameters(
                    loanAmountInEuro: EXAMPLE_LOAN_AMOUNT,
                    debitInterestFactor: EXAMPLE_DEBIT_INTEREST,
                    initialAmortizationFactor: EXAMPLE_INITIAL_AMORTIZATION,
                    fixedInterestRateInYears: EXAMPLE_FIXED_INTEREST_RATE)
        }

        if (isRequiredOptionMissing(options)) {
            printErrorMessageAndUsage('To calculate an amortization table, please specify the loan amount, the debit interest, the initial amortization, and the fixed interest rate.')
            return null
        }

        if (areArgumentsHavingWrongType(options)) {
            printErrorMessageAndUsage('Please make sure to use decimal values for the loan amount, the debit interest, and the initial amortization while the fixed interest rate has to be provided as an integer.')
            return null
        }

        if (isArgumentLessOrEqualToZero(options.la)) {
            printArgumentHasToBeGreaterThanZero('loan amount', options.la)
            return null
        }

        if (isArgumentLessOrEqualToZero(options.di)) {
            printArgumentHasToBeGreaterThanZero('debit interest', options.di)
            return null
        }

        if (isArgumentLessOrEqualToZero(options.ia)) {
            printArgumentHasToBeGreaterThanZero('initial amortization', options.ia)
            return null
        }

        if (isArgumentLessOrEqualToZero(options.fir)) {
            printArgumentHasToBeGreaterThanZero('fixed interest rate', options.fir)
            return null
        }

        new AmortizationTableParameters(
                loanAmountInEuro: options.la as BigDecimal,
                debitInterestFactor: options.di as BigDecimal,
                initialAmortizationFactor: options.ia as BigDecimal,
                fixedInterestRateInYears: options.fir as int)
    }

    private boolean isArgumentLessOrEqualToZero(def option) {
        (option as BigDecimal).setScale(2, HALF_UP) <= ZERO_WITH_SCALE_2
    }

    private void defineCommandLineInterface() {
        cliBuilder.with {
            '?'(longOpt: 'help', 'print this message')
            di(longOpt: 'debitInterest', args: 1, argName: 'interest', "the debit interest in percent, e.g. $EXAMPLE_DEBIT_INTEREST")
            ex(longOpt: 'example', "same as -la $EXAMPLE_LOAN_AMOUNT -di $EXAMPLE_DEBIT_INTEREST -ia $EXAMPLE_INITIAL_AMORTIZATION -fir $EXAMPLE_FIXED_INTEREST_RATE")
            fir(longOpt: 'fixedInterestRate', args: 1, argName: 'interest', "the fixed interest rate in whole-number years, e.g. $EXAMPLE_FIXED_INTEREST_RATE")
            ia(longOpt: 'initialAmortization', args: 1, argName: 'amortization', "the initial amortization in percent, e.g. $EXAMPLE_INITIAL_AMORTIZATION")
            la(longOpt: 'loanAmount', args: 1, argName: 'amount', "the loan amount in EUR, e.g. $EXAMPLE_LOAN_AMOUNT")
        }
        cliDefined = true
    }

    private boolean isArgumentForOptionMissing(OptionAccessor options) {
        !options
    }

    private boolean isHelpNeeded(String[] args, OptionAccessor options) {
        (args.size() == 0) || options.'?'
    }

    private boolean shallExampleValuesBeUsed(OptionAccessor options) {
        options.ex
    }

    private boolean isRequiredOptionMissing(OptionAccessor options) {
        !options.la || !options.di || !options.ia || !options.fir
    }

    private boolean areArgumentsHavingWrongType(OptionAccessor options) {
        !options.la.isBigDecimal() || !options.di.isBigDecimal() || !options.ia.isBigDecimal() || !options.fir.isInteger()
    }

    private void printErrorMessageAndUsage(String errorMessage) {
        println "Error: $errorMessage"
        println ""
        cliBuilder.usage()
    }

    private void printArgumentHasToBeGreaterThanZero(String option, String argument) {
        printErrorMessageAndUsage("The $option has to a value greater than zero (or 0.00) but was $argument.")
    }
}
