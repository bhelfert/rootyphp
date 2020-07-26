package de.bhelfert.rootyphp.amortizationtable.util

import java.time.LocalDate

class DateUtil {

    LocalDate getEndOfMonth(LocalDate date) {
        date.withDayOfMonth(date.lengthOfMonth())
    }
}
