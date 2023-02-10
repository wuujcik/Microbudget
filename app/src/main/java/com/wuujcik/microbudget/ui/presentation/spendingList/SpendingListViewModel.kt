package com.wuujcik.microbudget.ui.presentation.spendingList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wuujcik.data.entities.Spending
import com.wuujcik.domain.repository.SpendingsRepository
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.Screen
import com.wuujcik.microbudget.ui.presentation.itemDetail.ItemDetailFragmentArgs
import com.wuujcik.microbudget.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SpendingListViewModel(
    private val spendingsRepository: SpendingsRepository
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _state = MutableStateFlow(SpendingListState())
    val state: StateFlow<SpendingListState>
        get() = _state

    init {
        viewModelScope.launch {
            spendingsRepository.getAll().collect { listOfSpending ->
                _state.value = SpendingListState(listOfSpending = listOfSpending.sortedBy { it.date })
            }
        }
    }

    fun addNew() {
        _navigateTo.value = Event(Screen(R.id.item_detail_fragment))
    }

    fun edit(spending: Spending) {
        _navigateTo.value = Event(
            Screen(
                R.id.item_detail_fragment,
                ItemDetailFragmentArgs(item = spending).toBundle()
            )
        )
    }

    fun deleteOne(spending: Spending) = viewModelScope.launch {
        spendingsRepository.deleteOne(spending)
    }

    fun deleteAll() = viewModelScope.launch { // TODO: allow deleting all
        spendingsRepository.deleteAll()
    }
}
