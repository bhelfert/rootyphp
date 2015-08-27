package com.sevenlist.rootyphp.amortizationtable.cli

import com.sevenlist.rootyphp.amortizationtable.model.AmortizationTableParameters
import com.sevenlist.rootyphp.amortizationtable.model.TestData
import spock.lang.Specification
import spock.lang.Unroll

class CommandLineParserTest extends Specification {

    CommandLineParser parser = new CommandLineParser()

    def "CLI is defined only once"() {
        given:
        CliBuilder cliBuilderMock = GroovyMock()
        String[] someArgs = []

        when:
        CommandLineParser parserUsingMock = new CommandLineParser(cliBuilder: cliBuilderMock)
        parserUsingMock.parseCommandLine(someArgs)
        parserUsingMock.parseCommandLine(someArgs)

        then:
        1 * cliBuilderMock.with { _ }
    }

    def "returns null when argument of option is missing"() {
        given:
        def loanAmountOptionWithoutAmount = '-la'

        when:
        def parameters = parser.parseCommandLine(loanAmountOptionWithoutAmount)

        then:
        !parameters
    }

    @Unroll
    def "prints usage and returns null when help option #arg is specified"() {
        given:
        CliBuilder cliBuilderSpy = GroovySpy(global: true)
        CommandLineParser parserUsingSpy = new CommandLineParser(cliBuilder: cliBuilderSpy)

        when:
        def parameters = parserUsingSpy.parseCommandLine(arg)

        then:
        1 * cliBuilderSpy.usage()
        !parameters

        where:
        arg << ['-?', '--help']
    }

    @Unroll
    def "returns amortization table parameters with example values when example option #arg is specified"() {
        when:
        def parameters = parser.parseCommandLine(arg)

        then:
        parameters == TestData.PARAMETERS

        where:
        arg << ['-ex', '--example']
    }

    @Unroll
    def "prints usage and returns null when required option/s #missingOptions is/are missing"() {
        given:
        CliBuilder cliBuilderSpy = GroovySpy(global: true)
        CommandLineParser parserUsingSpy = new CommandLineParser(cliBuilder: cliBuilderSpy)

        when:
        def parameters = parserUsingSpy.parseCommandLine(argsWithMissingOption as String[])

        then:
        1 * cliBuilderSpy.usage()
        !parameters

        where:
        argsWithMissingOption          | missingOptions
        ['-la', 1, '-di', 2, '-ia', 3] | '-fir'
        ['-ia', 3, '-fir', 4]          | '-la and -di'
        ['-di', 2]                     | '-la, -ia, and -fir'
    }

    @Unroll
    def "prints usage and returns null when argument/s #argNames is/are of wrong type"() {
        given:
        CliBuilder cliBuilderSpy = GroovySpy(global: true)
        CommandLineParser parserUsingSpy = new CommandLineParser(cliBuilder: cliBuilderSpy)

        when:
        def parameters = parserUsingSpy.parseCommandLine(argsWithWrongType as String[])

        then:
        1 * cliBuilderSpy.usage()
        !parameters

        where:
        argsWithWrongType                                                  | argNames
        ['-la', 'wrong', '-di', 2.22, '-ia', 3.33, '-fir', 4]              | '-la'
        ['-la', '1wrong', '-di', '2,22', '-ia', 3, '-fir', 4]              | '-la and -di'
        ['-la', '1wrong', '-di', '2,22', '-ia', 'wrong3.33', '-fir', 4]    | '-la, -di, and -ia'
        ['-la', '1wrong', '-di', '2,22', '-ia', 'wrong3.33', '-fir', 4.44] | '-la, -di, -ia, and -fir'
        ['-la', ' ', '-di', ' ', '-ia', ' ', '-fir', ' ']                  | '-la, -di, -ia, and -fir (having spaces)'
    }

    @Unroll
    def "prints usage and returns null when argument #argName is equal to 0.00"() {
        given:
        CliBuilder cliBuilderSpy = GroovySpy(global: true)
        CommandLineParser parserUsingSpy = new CommandLineParser(cliBuilder: cliBuilderSpy)

        when:
        def parameters = parserUsingSpy.parseCommandLine(argsWithToSmallValue as String[])

        then:
        1 * cliBuilderSpy.usage()
        !parameters

        where:
        argsWithToSmallValue                               | argName
        ['-la', 0, '-di', 2.22, '-ia', 3.33, '-fir', 4]    | '-la 0'
        ['-la', 0.00, '-di', 2.22, '-ia', 3.33, '-fir', 4] | '-la 0.00'

        ['-la', 1, '-di', 0, '-ia', 3.33, '-fir', 4]       | '-di 0'
        ['-la', 1, '-di', 0.00, '-ia', 3.33, '-fir', 4]    | '-di 0.00'

        ['-la', 1, '-di', 2.22, '-ia', 0, '-fir', 4]       | '-ia 0'
        ['-la', 1, '-di', 2.22, '-ia', 0.00, '-fir', 4]    | '-ia 0.00'

        ['-la', 1, '-di', 2.22, '-ia', 3.33, '-fir', 0]    | '-fir'
    }

    @Unroll
    def "prints usage and returns null when argument #argName is less that 0.00"() {
        given:
        CliBuilder cliBuilderSpy = GroovySpy(global: true)
        CommandLineParser parserUsingSpy = new CommandLineParser(cliBuilder: cliBuilderSpy)

        when:
        def parameters = parserUsingSpy.parseCommandLine(argsWithToSmallValue as String[])

        then:
        1 * cliBuilderSpy.usage()
        !parameters

        where:
        argsWithToSmallValue                                | argName
        ['-la', 0.001, '-di', 2.22, '-ia', 3.33, '-fir', 4] | '-la'
        ['-la', 1, '-di', 0.001, '-ia', 3.33, '-fir', 4]    | '-di'
        ['-la', 1, '-di', 2.22, '-ia', 0.001, '-fir', 4]    | '-ia'
    }


    def "returns amortization table parameters with the values specified"() {
        given:
        def loanAmount = 100
        def debitInterest = 2.34
        def initialAmortization = 5.67
        def fixedInterestRate = 8

        String[] args = [
                '--loanAmount', loanAmount,
                '--debitInterest', debitInterest,
                '--initialAmortization', initialAmortization,
                '--fixedInterestRate', fixedInterestRate
        ]

        def expectedParameters = new AmortizationTableParameters(
                loanAmountInEuro: loanAmount,
                debitInterestFactor: debitInterest,
                initialAmortizationFactor: initialAmortization,
                fixedInterestRateInYears: fixedInterestRate
        )

        when:
        def parameters = parser.parseCommandLine(args)

        then:
        parameters == expectedParameters
    }
}
