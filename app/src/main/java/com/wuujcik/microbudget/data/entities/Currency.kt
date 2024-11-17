package com.wuujcik.microbudget.data.entities

enum class Currency(val currencyCode: String, val currencySymbol: String) {
    CZECH("CZK", "Kč"),
    POLISH("PLN", "zł"),
    EURO("EUR", "€");

    companion object {
        private val map = values().associateBy(Currency::currencyCode)

        fun currencyFromCode(code: String?) = map[code?.uppercase()]
    }
}