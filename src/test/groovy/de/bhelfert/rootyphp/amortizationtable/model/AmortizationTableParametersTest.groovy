package de.bhelfert.rootyphp.amortizationtable.model

import spock.lang.Specification

class AmortizationTableParametersTest extends Specification {

    def "calculates debit interest factor from percentage value"() {
        when:
        AmortizationTableParameters params = [debitInterestFactor: 1]

        then:
        params.debitInterestFactor == 0.01
    }

    def "calculates initial amortization factor from percentage value"() {
        when:
        AmortizationTableParameters params = [initialAmortizationFactor: 1]

        then:
        params.initialAmortizationFactor == 0.01
    }
}
