package com.wuujcik.microbudget.ui.presentation.itemDetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.repository.SpendingsRepository
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.Screen
import com.wuujcik.microbudget.util.Event
import kotlinx.coroutines.launch
import java.time.ZonedDateTime

class ItemDetailViewModel(
    val originalItem: Spending?,
    private val spendingsRepository: SpendingsRepository
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _chosenDate = mutableStateOf<ZonedDateTime>(originalItem?.date ?: ZonedDateTime.now())
    val chosenDate: ZonedDateTime
        get() = _chosenDate.value

    val availableCurrencies = Currency.values().toList()

    fun onSaveClicked(spending: Spending) = viewModelScope.launch {
        spendingsRepository.insertOrUpdate(spending)
        _navigateTo.value = Event(Screen(R.id.spending_list_fragment))
    }

    fun onDateChosen(date: ZonedDateTime?) {
        _chosenDate.value = date ?: ZonedDateTime.now()
    }
}
