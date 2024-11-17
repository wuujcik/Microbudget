package com.wuujcik.microbudget.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wuujcik.microbudget.util.serializers.LocalDate
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = Transaction.TABLE_NAME)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: LocalDate,
    val purpose: String,
    val amount: Double,
    val currency: Currency
) {
    companion object {
        const val TABLE_NAME = "Transactions"
    }
}
