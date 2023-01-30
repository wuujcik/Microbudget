package com.wuujcik.domain.database

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import com.wuujcik.data.entities.Currency

object CurrencyAdapter {
    @FromJson
    fun decode(value: String?): Currency? {
        return value?.let {
            Currency.values().firstOrNull { enum -> enum.currencyCode == it }
        }
    }

    @ToJson
    fun encode(value: Currency?): String? {
        return value?.currencyCode
    }
}