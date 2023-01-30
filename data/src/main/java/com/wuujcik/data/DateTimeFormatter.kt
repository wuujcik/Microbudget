package com.wuujcik.data

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object DateTimeFormatter {

    fun ZonedDateTime?.toEuropeanDateFormat(): String {
        this ?: return ""
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(this)
    }
}
