package com.wuujcik.microbudget.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wuujcik.microbudget.util.serializers.LocalDate
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.Serializable
import java.time.ZonedDateTime

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

@Parcelize
@Entity(tableName = Spending.TABLE_NAME)
data class Spending(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: ZonedDateTime,
    val purpose: String,
    val amount: Double,
    val currency: Currency
) : Parcelable {
    companion object {
        const val TABLE_NAME = "Spending"
    }
}
