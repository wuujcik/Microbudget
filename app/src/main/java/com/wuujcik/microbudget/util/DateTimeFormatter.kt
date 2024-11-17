package com.wuujcik.microbudget.util

import com.wuujcik.microbudget.util.serializers.LocalDate
import java.time.format.DateTimeFormatter

object DateTimeFormatter {

    fun LocalDate?.toEuropeanDateFormat(): String {
        this ?: return ""
        return DateTimeFormatter.ofPattern("dd.MM.yyyy").format(this)
    }
}
