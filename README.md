Amortization Table Calculator for Rootyphp
==========================================

About this project
------------------
This application generates an amortization table based on the loan amount, debit interest, initial amortization, and the
fixed interest rate the user specifies using the command line. The calculations are based on the formulas found in
[this paper](http://finanzmathe.mathe-lok.de/downloads/waswarwichtig6u7.pdf).

From a technical point of view, the code is written in [Groovy](http://www.groovy-lang.org/), tested with
[Spock](https://github.com/spockframework/spock), and build by [Gradle](https://gradle.org/).
[Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) is required  as the code uses the new Date
and Time API ([JSR-310](https://jcp.org/en/jsr/detail?id=310)).

The project started out using the [Spring Framework](http://projects.spring.io/spring-framework/) for dependency injection
as it nicely works with Groovy and nicely integrates with Spock but everything was so simple that it was just overkill in
every way -- so it got removed.

To get the precision needed for financial calculations, [BigDecimal](https://docs.oracle.com/javase/8/docs/api/java/math/BigDecimal.html)
objects are used. As Rootyphp's task description contains some rounding errors in its amortization table, the values calculated
by this project will differ a bit due to having less rounding errors.

If you have any questions please do not hesitate to contact me via <sevenlist@web.de>.

How to run/use the application
------------------------------

### Preconditions
1. Make sure your JAVA_HOME environment variable points to your
[Java SE 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html) installation.
2. Make sure you have a locally installed distribution under:
```
./build/install/amortizationTableCalculator
```
If there is none, create it by running:
```
./gradlew clean installDist
```
If your build fails with "unable to resolve class java.time.LocalDate" errors, your JAVA_HOME environment variable does
not point to your Java SE 8 installation.

### Quick run with example data
To generate the example table listed in Rootyphp's task description, just type into your command line:
```
./build/install/amortizationTableCalculator/bin/amortizationTableCalculator -ex
```
You will then see an output similar to:
```
Tilgungsplan für
* Darlehensbetrag:     100.000,00 €
* Sollzins:            1,58%
* Anfängliche Tilgung: 2%
* Zinsbindung:         10 Jahre

+------------------+---------------+-------------+------------------------------+---------------+
|       Datum      |   Restschuld  |    Zinsen   | Tilgung (+) / Auszahlung (-) |      Rate     |
+------------------+---------------+-------------+------------------------------+---------------+
|       31.08.2015 | -100.000,00 € |      0,00 € |                -100.000,00 € | -100.000,00 € |
|       30.09.2015 |  -99.833,33 € |    131,67 € |                     166,67 € |      298,33 € |
|       31.10.2015 |  -99.666,45 € |    131,45 € |                     166,89 € |      298,33 € |
|       30.11.2015 |  -99.499,34 € |    131,23 € |                     167,11 € |      298,33 € |
|       31.12.2015 |  -99.332,02 € |    131,01 € |                     167,33 € |      298,33 € |
|       31.01.2016 |  -99.164,47 € |    130,79 € |                     167,55 € |      298,33 € |
|       29.02.2016 |  -98.996,70 € |    130,57 € |                     167,77 € |      298,33 € |
|       31.03.2016 |  -98.828,71 € |    130,35 € |                     167,99 € |      298,33 € |
|       30.04.2016 |  -98.660,51 € |    130,12 € |                     168,21 € |      298,33 € |
|       31.05.2016 |  -98.492,08 € |    129,90 € |                     168,43 € |      298,33 € |
|       30.06.2016 |  -98.323,42 € |    129,68 € |                     168,65 € |      298,33 € |
|       31.07.2016 |  -98.154,55 € |    129,46 € |                     168,87 € |      298,33 € |
|       31.08.2016 |  -97.985,45 € |    129,24 € |                     169,10 € |      298,33 € |
|       30.09.2016 |  -97.816,13 € |    129,01 € |                     169,32 € |      298,33 € |
|       31.10.2016 |  -97.646,59 € |    128,79 € |                     169,54 € |      298,33 € |
|       30.11.2016 |  -97.476,83 € |    128,57 € |                     169,77 € |      298,33 € |
|       31.12.2016 |  -97.306,84 € |    128,34 € |                     169,99 € |      298,33 € |
|             2017 |  -95.249,43 € |  1.522,59 € |                   2.057,41 € |    3.580,00 € |
|             2018 |  -93.159,28 € |  1.489,85 € |                   2.090,15 € |    3.580,00 € |
|             2019 |  -91.035,86 € |  1.456,58 € |                   2.123,42 € |    3.580,00 € |
|             2020 |  -88.878,65 € |  1.422,79 € |                   2.157,21 € |    3.580,00 € |
|             2021 |  -86.687,11 € |  1.388,46 € |                   2.191,54 € |    3.580,00 € |
|             2022 |  -84.460,69 € |  1.353,58 € |                   2.226,42 € |    3.580,00 € |
|             2023 |  -82.198,83 € |  1.318,15 € |                   2.261,85 € |    3.580,00 € |
|             2024 |  -79.900,98 € |  1.282,15 € |                   2.297,85 € |    3.580,00 € |
|       31.01.2025 |  -79.707,85 € |    105,20 € |                     193,13 € |      298,33 € |
|       28.02.2025 |  -79.514,47 € |    104,95 € |                     193,38 € |      298,33 € |
|       31.03.2025 |  -79.320,83 € |    104,69 € |                     193,64 € |      298,33 € |
|       30.04.2025 |  -79.126,93 € |    104,44 € |                     193,89 € |      298,33 € |
|       31.05.2025 |  -78.932,78 € |    104,18 € |                     194,15 € |      298,33 € |
|       30.06.2025 |  -78.738,38 € |    103,93 € |                     194,41 € |      298,33 € |
|       31.07.2025 |  -78.543,72 € |    103,67 € |                     194,66 € |      298,33 € |
|       31.08.2025 |  -78.348,80 € |    103,42 € |                     194,92 € |      298,33 € |
| Zinsbindungsende |  -78.348,80 € | 14.148,80 € |                  21.651,20 € |   35.800,00 € |
+------------------+---------------+-------------+------------------------------+---------------+
```

### Usage
To use the Amortization Table Calculator run the following script on your command line:
```
./build/install/amortizationTableCalculator/bin/amortizationTableCalculator
```
You will then see the following output:
```
usage: amortizationTableCalculator
 -?,--help                                  print this message
 -di,--debitInterest <interest>             the debit interest in percent, e.g. 1.58
 -ex,--example                              same as -la 100000 -di 1.58 -ia 2 -fir 10
 -fir,--fixedInterestRate <interest>        the fixed interest rate in whole-number years, e.g. 10
 -ia,--initialAmortization <amortization>   the initial amortization in percent, e.g. 2
 -la,--loanAmount <amount>                  the loan amount in EUR, e.g. 100000
```

### Example
Let's make an example - you want to calculate the amortization table for these values:
* loan amount: EUR 200.000,00
* debit interest: 3.0%
* initial amortization: 1.0%
* fixed interest rate: 15 yrs

You then need to run the Amortization Table Calculator like this:
```
./build/install/amortizationTableCalculator/bin/amortizationTableCalculator -la 200000 -di 3.0 -ia 1.0 -fir 15
```

How to build the project or a distribution
------------------------------------------
Depending on if you want to
* merely build the project and execute its tests ("build"),
* run the application ("run"),
* create an installed distribution ("installDist"), or
* make distributables as TAR and ZIP files ("assemble")

you need to run the Gradle Wrapper on your command line with the according tasks:
```
./gradlew clean [build|run|installDist|assemble]
```
For more information on the last three tasks see the documentation on Gradle's
[Application Plugin](https://docs.gradle.org/2.6/userguide/application_plugin.html).

Terminology / Vocabulary
------------------------
As I am not familiar with the terminology, I provide here the one I have used:
* Anfängliche Tilgung        = initial amortization
* Auszahlung                 = payout
* Darlehensbetrag            = loan amount
* Rate                       = rate
* Restschuld                 = residual debt
* Sollzins                   = debit interest
* Tilgung                    = amortization
* Tilgungsplan               = amortization table
* Unterperiodischer Zinssatz = underperiodic interest
* Zinsbindung                = fixed interest rate
* Zinsbindungsende	     = end of fixed interest rate
* Zinsen                     = interest

Project statistics
------------------
...can be generated by using:
```
./gradlew stats
```

The current statistics are:
```
+----------------------+-------+-------+
| Name                 | Files |  LOC  |
+----------------------+-------+-------+
| Groovy Sources       |    12 |   505 |
| Groovy Test Sources  |    13 |   561 |
+----------------------+-------+-------+
| Totals               |    25 |  1066 |
+----------------------+-------+-------+
```

Project dependencies
--------------------
...can be queried by using:
```
./gradlew -q dependencies
```

The current dependencies are:
```
+--- :btc-ascii-table:1.0
+--- commons-cli:commons-cli:1.3.1
+--- org.codehaus.groovy:groovy-all:2.4.4
+--- cglib:cglib-nodep:3.1
\--- org.spockframework:spock-core:1.0-groovy-2.4
     +--- org.codehaus.groovy:groovy-all:2.4.1 -> 2.4.4
     \--- junit:junit:4.12
          \--- org.hamcrest:hamcrest-core:1.3
```
