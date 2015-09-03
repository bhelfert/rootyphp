package com.sevenlist.rootyphp.amortizationtable

import groovy.ui.SystemOutputInterceptor
import spock.lang.Specification

class EndToEndTest extends Specification {

    def "using valid arguments results in a correctly calculated and printed amortization table"() {
        given:
        StringBuilder output = new StringBuilder()
        SystemOutputInterceptor soutInterceptor = new SystemOutputInterceptor({
            output.append(it)
            false
        })

        String[] args = [
                '-la', 200000,
                '-di', 1.43,
                '-ia', 2.5,
                '-fir', 15
        ]

        when:
        soutInterceptor.start()
        AmortizationTableCalculator.main(args)
        soutInterceptor.stop()

        then:
        def outputAsString = output.toString()
        outputAsString.contains("""
Tilgungsplan für
* Darlehensbetrag:     200.000,00 €
* Sollzins:            1,43%
* Anfängliche Tilgung: 2,5%
* Zinsbindung:         15 Jahre

+------------------+---------------+-------------+------------------------------+---------------+
|       Datum      |   Restschuld  |    Zinsen   | Tilgung (+) / Auszahlung (-) |      Rate     |
+------------------+---------------+-------------+------------------------------+---------------+
|       30.09.2015 | -200.000,00 € |      0,00 € |                -200.000,00 € | -200.000,00 € |
|       31.10.2015 | -199.583,33 € |    238,33 € |                     416,67 € |      655,00 € |
|       30.11.2015 | -199.166,17 € |    237,84 € |                     417,16 € |      655,00 € |
|       31.12.2015 | -198.748,51 € |    237,34 € |                     417,66 € |      655,00 € |
|       31.01.2016 | -198.330,35 € |    236,84 € |                     418,16 € |      655,00 € |
|       29.02.2016 | -197.911,70 € |    236,34 € |                     418,66 € |      655,00 € |
|       31.03.2016 | -197.492,54 € |    235,84 € |                     419,16 € |      655,00 € |
|       30.04.2016 | -197.072,89 € |    235,35 € |                     419,65 € |      655,00 € |
|       31.05.2016 | -196.652,73 € |    234,85 € |                     420,15 € |      655,00 € |
|       30.06.2016 | -196.232,08 € |    234,34 € |                     420,66 € |      655,00 € |
|       31.07.2016 | -195.810,92 € |    233,84 € |                     421,16 € |      655,00 € |
|       31.08.2016 | -195.389,26 € |    233,34 € |                     421,66 € |      655,00 € |
|       30.09.2016 | -194.967,10 € |    232,84 € |                     422,16 € |      655,00 € |
|       31.10.2016 | -194.544,43 € |    232,34 € |                     422,66 € |      655,00 € |
|       30.11.2016 | -194.121,27 € |    231,83 € |                     423,17 € |      655,00 € |
|       31.12.2016 | -193.697,59 € |    231,33 € |                     423,67 € |      655,00 € |
|             2017 | -188.573,98 € |  2.736,38 € |                   5.123,62 € |    7.860,00 € |
|             2018 | -183.376,61 € |  2.662,63 € |                   5.197,37 € |    7.860,00 € |
|             2019 | -178.104,43 € |  2.587,82 € |                   5.272,18 € |    7.860,00 € |
|             2020 | -172.756,36 € |  2.511,93 € |                   5.348,07 € |    7.860,00 € |
|             2021 | -167.331,31 € |  2.434,95 € |                   5.425,05 € |    7.860,00 € |
|             2022 | -161.828,17 € |  2.356,86 € |                   5.503,14 € |    7.860,00 € |
|             2023 | -156.245,82 € |  2.277,65 € |                   5.582,35 € |    7.860,00 € |
|             2024 | -150.583,12 € |  2.197,30 € |                   5.662,70 € |    7.860,00 € |
|             2025 | -144.838,91 € |  2.115,79 € |                   5.744,21 € |    7.860,00 € |
|             2026 | -139.012,01 € |  2.033,10 € |                   5.826,90 € |    7.860,00 € |
|             2027 | -133.101,24 € |  1.949,23 € |                   5.910,77 € |    7.860,00 € |
|             2028 | -127.105,39 € |  1.864,15 € |                   5.995,85 € |    7.860,00 € |
|             2029 | -121.023,24 € |  1.777,85 € |                   6.082,15 € |    7.860,00 € |
|       31.01.2030 | -120.512,46 € |    144,22 € |                     510,78 € |      655,00 € |
|       28.02.2030 | -120.001,07 € |    143,61 € |                     511,39 € |      655,00 € |
|       31.03.2030 | -119.489,07 € |    143,00 € |                     512,00 € |      655,00 € |
|       30.04.2030 | -118.976,46 € |    142,39 € |                     512,61 € |      655,00 € |
|       31.05.2030 | -118.463,24 € |    141,78 € |                     513,22 € |      655,00 € |
|       30.06.2030 | -117.949,41 € |    141,17 € |                     513,83 € |      655,00 € |
|       31.07.2030 | -117.434,97 € |    140,56 € |                     514,44 € |      655,00 € |
|       31.08.2030 | -116.919,91 € |    139,94 € |                     515,06 € |      655,00 € |
|       30.09.2030 | -116.404,24 € |    139,33 € |                     515,67 € |      655,00 € |
| Zinsbindungsende | -116.404,24 € | 34.304,24 € |                  83.595,76 € |  117.900,00 € |
+------------------+---------------+-------------+------------------------------+---------------+
""")
        println outputAsString
    }
}
