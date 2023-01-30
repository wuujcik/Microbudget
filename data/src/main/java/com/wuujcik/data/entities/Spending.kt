package com.wuujcik.data.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import java.time.ZonedDateTime

@JsonClass(generateAdapter = true)
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
