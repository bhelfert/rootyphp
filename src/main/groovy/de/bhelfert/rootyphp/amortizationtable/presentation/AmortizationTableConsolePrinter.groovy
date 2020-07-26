package de.bhelfert.rootyphp.amortizationtable.presentation

import com.bethecoder.ascii_table.ASCIITable
import de.bhelfert.rootyphp.amortizationtable.model.AmortizationTableLine
import groovy.transform.ToString

import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@ToString(includeFields = true, includeNames = true)
class AmortizationTableConsolePrinter {

    private static final DateTimeFormatter GERMAN_DATE_FORMAT = DateTimeFormatter.ofPattern('dd.MM.yyyy')
    private static final DateTimeFormatter YEAR_ONLY_FORMAT = DateTimeFormatter.ofPattern('y')

    private AmortizationTableOutputPreparer outputPreparer = new AmortizationTableOutputPreparer()
    private List amortizationTableLinesToPrint

    void printAmortizationTable(List amortizationTableLines) {
        amortizationTableLinesToPrint = outputPreparer.prepareTableLinesForOutput(amortizationTableLines)
        printHeaderDisplayingParametersOfCalculation()
        printTable()
    }

    private void printHeaderDisplayingParametersOfCalculation() {
        AmortizationTableLine firstTableLine = amortizationTableLinesToPrint.first()
        println """
Tilgungsplan für
* Darlehensbetrag:     ${asEuro(firstTableLine.loanAmountInEuro)}
* Sollzins:            ${asPercent(firstTableLine.debitInterestFactor)}
* Anfängliche Tilgung: ${asPercent(firstTableLine.initialAmortizationFactor)}
* Zinsbindung:         ${asYears(firstTableLine.fixedInterestRateInYears)}
"""
    }

    private void printTable() {
        String[] header = ['Datum', 'Restschuld', 'Zinsen', 'Tilgung (+) / Auszahlung (-)', 'Rate']
        String[][] data = createDataForAllAmortizationTableLines()
        ASCIITable.instance.printTable(header, data);
    }

    private String[][] createDataForAllAmortizationTableLines() {
        String[][] data = new String[amortizationTableLinesToPrint.size()][]
        amortizationTableLinesToPrint.eachWithIndex { AmortizationTableLine line, i ->
            data[i] = createDataForSingleAmortizationTableLine(line)
        }
        data
    }

    private String[] createDataForSingleAmortizationTableLine(AmortizationTableLine line) {
        [
                asGermanDate(line.date), // Datum
                asEuro(line.residualDebt), // Restschuld
                asEuro(line.interest), // Zinsen
                asEuro(line.amortization), // Tilgung/Auszahlung
                asEuro(line.rate) // Rate
        ]
    }

    private String asEuro(def value) {
        NumberFormat.currencyInstance.format(value)
    }

    private String asPercent(def valueAsFactor) {
        "${NumberFormat.instance.format(valueAsFactor * 100)}%"
    }

    private String asYears(def value) {
        "$value Jahre"
    }

    private String asGermanDate(LocalDate date) {
        if (!date) {
            return "Zinsbindungsende"
        }
        isOnlyYearToBePrinted(date) ? date.format(YEAR_ONLY_FORMAT) : date.format(GERMAN_DATE_FORMAT)
    }

    private boolean isOnlyYearToBePrinted(LocalDate date) {
        outputPreparer.hasDateBeenAggregated(date)
    }
}
