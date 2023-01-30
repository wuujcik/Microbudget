package com.wuujcik.microbudget.util

import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


fun Long?.toZonedDateTime(): ZonedDateTime? {
    this ?: return null
    val instant: Instant = Instant.ofEpochMilli(this)
    return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
}

fun ZonedDateTime?.toMilliseconds(): Long? {
    this ?: return null
    return this.toInstant().toEpochMilli()
}