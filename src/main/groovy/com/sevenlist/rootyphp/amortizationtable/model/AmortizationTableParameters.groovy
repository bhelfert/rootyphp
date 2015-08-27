package com.sevenlist.rootyphp.amortizationtable.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString(includeNames = true)
class AmortizationTableParameters {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100)

    BigDecimal loanAmountInEuro
    BigDecimal debitInterestFactor
    BigDecimal initialAmortizationFactor
    int fixedInterestRateInYears

    void setDebitInterestFactor(BigDecimal debitInterestInPercent) {
        debitInterestFactor = debitInterestInPercent / ONE_HUNDRED
    }

    void setInitialAmortizationFactor(BigDecimal initialAmortizationInPercent) {
        initialAmortizationFactor = initialAmortizationInPercent / ONE_HUNDRED
    }
}
