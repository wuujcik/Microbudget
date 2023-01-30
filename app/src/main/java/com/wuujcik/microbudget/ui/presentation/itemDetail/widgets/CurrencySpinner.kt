package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wuujcik.data.entities.Currency
import com.wuujcik.microbudget.ui.common.SpinnerWidget

@Composable
fun CurrencySpinner(
    availableCurrencies: List<Currency>,
    selectedItem: Currency,
    onItemSelected: (Currency) -> Unit
) {

    SpinnerWidget(
        modifier = Modifier.wrapContentSize(),
        dropDownModifier = Modifier.wrapContentSize(),
        items = availableCurrencies,
        selectedItem = selectedItem,
        onItemSelected = onItemSelected,
        selectedItemFactory = { modifier, item ->
            Row(
                modifier = modifier
                    .padding(8.dp)
                    .wrapContentSize()
            ) {
                Text(item.currencyCode)

                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = null
                )
            }
        },
        dropdownItemFactory = { item, _ ->
            Text(text = item.currencyCode)
        }
    )
}