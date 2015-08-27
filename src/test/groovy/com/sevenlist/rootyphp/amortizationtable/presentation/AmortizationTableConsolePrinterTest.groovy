package com.sevenlist.rootyphp.amortizationtable.presentation

import com.sevenlist.rootyphp.amortizationtable.model.TestData
import spock.lang.Specification

/**
 * The presentation of the header and the table is tested by the EndToEndTest.
 */
class AmortizationTableConsolePrinterTest extends Specification {

    def "printing an amortization tables causes the output to be prepared before"() {
        given:
        AmortizationTableOutputPreparer outputPreparerMock = Mock()
        AmortizationTableConsolePrinter consolePrinter = new AmortizationTableConsolePrinter(outputPreparer: outputPreparerMock)
        List amortizationTableLines = [TestData.PAYOUT_LINE]

        when:
        consolePrinter.printAmortizationTable(amortizationTableLines)

        then:
        1 * outputPreparerMock.prepareTableLinesForOutput(amortizationTableLines) >> amortizationTableLines
    }
}
