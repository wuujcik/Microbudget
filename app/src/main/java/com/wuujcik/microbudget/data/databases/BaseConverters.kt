package com.wuujcik.microbudget.data.databases

import androidx.room.TypeConverter
import com.wuujcik.microbudget.util.serializers.LocalDate
import com.wuujcik.microbudget.util.serializers.LocalDateSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

open class BaseConverters {
    companion object {
        val json =
            Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            }
    }

    @TypeConverter
    fun fromDate(date: LocalDate?): String? {
        return date?.let { DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(it) }
    }

    @TypeConverter
    fun fromString(string: String?): LocalDate? {
        return string?.let { LocalDate.parse(it) }
    }

    @TypeConverter
    fun toStringList(value: String) = json.decodeFromString<List<String>>(value)

    @TypeConverter
    fun fromStringList(value: List<String>) = json.encodeToString(value)

    @TypeConverter
    fun toLocalDate(value: String) = json.decodeFromString(LocalDateSerializer, value)

    @TypeConverter
    fun fromLocalDate(value: LocalDate) = json.encodeToString(LocalDateSerializer, value)

    @TypeConverter
    fun fromDate(zonedDateTime: ZonedDateTime?): String? {
        return zonedDateTime?.let { DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(it) }
    }

    @TypeConverter
    fun fromZonedString(string: String?): ZonedDateTime? {
        return string?.let { ZonedDateTime.parse(it)}
    }
}
