package com.wuujcik.domain.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.time.DateTimeException
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object ZonedDateTimeAdapter {
    @Throws(DateTimeParseException::class)
    @FromJson
    fun decode(value: String): ZonedDateTime? {
        return value.let { ZonedDateTime.parse(it) }
    }

    @Throws(DateTimeException::class)
    @ToJson
    fun encode(value: ZonedDateTime): String {
        return value.let { DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(it) }
    }
}


@ProvidedTypeConverter
class ZonedDateTimeTypeConverter {
    @TypeConverter
    fun fromDate(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.let { DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(it) }
    }

    @TypeConverter
    fun fromString(string: String?): ZonedDateTime? {
        return string?.let { ZonedDateTime.parse(it)}
    }
}
