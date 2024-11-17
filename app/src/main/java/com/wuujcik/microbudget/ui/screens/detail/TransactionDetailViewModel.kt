package com.wuujcik.microbudget.ui.screens.detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.microbudget.data.entities.Currency
import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.data.repositories.TransactionsRepository
import com.wuujcik.microbudget.util.serializers.LocalDate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    private val transactionsRepository: TransactionsRepository
) : ViewModel() {

    sealed interface Event {
        data object ItemSaved : Event
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    var isInitialized by mutableStateOf(false)
        private set

    private var originalItem by mutableStateOf<Transaction?>(null)

    val availableCurrencies = Currency.entries
    val selectedCurrency = mutableStateOf(Currency.CZECH)
    val selectedDate = mutableStateOf<LocalDate>(LocalDate.now())

    fun initialize(
        originalItem: Transaction?,
    ) {
        this.originalItem = originalItem
        selectedDate.value = originalItem?.date ?: LocalDate.now()
        selectedCurrency.value = originalItem?.currency ?: Currency.CZECH
        isInitialized = true
    }


    fun onSaveClicked(
        purpose: String,
        amountText: String
    ) = viewModelScope.launch {
        val itemId = originalItem?.id
        val amount = try {
            amountText.toDouble()
        } catch (e: Exception) {
            0.0
        }

        val transaction = if (itemId != null) {
            Transaction(
                id = itemId,
                date = selectedDate.value,
                purpose = purpose,
                amount = amount,
                currency = selectedCurrency.value
            )
        } else {
            Transaction(
                date = selectedDate.value,
                purpose = purpose,
                amount = amount,
                currency = selectedCurrency.value
            )
        }
        transactionsRepository.insertOrUpdate(transaction)
        eventChannel.send(Event.ItemSaved)
    }

    fun onDateChosen(date: LocalDate?) {
        selectedDate.value = date ?: LocalDate.now()
    }

    fun onCurrencyChanged(currency: Currency) {
        selectedCurrency.value = currency
    }
}
