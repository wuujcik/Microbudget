package com.wuujcik.microbudget.ui.presentation.spendingList.widgets

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.wuujcik.data.entities.Currency
import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.presentation.MenuItemData
import com.wuujcik.microbudget.ui.presentation.spendingList.SpendingListState
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.ZonedDateTime

sealed interface SpendingListEvent {
    object AddNew : SpendingListEvent
    object DeleteAll: SpendingListEvent
    object ShowAbout: SpendingListEvent
    data class Edit(val spending: Spending) : SpendingListEvent
    data class Delete(val spending: Spending) : SpendingListEvent
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfSpendingWidget(
    state: StateFlow<SpendingListState>,
    onEvent: ((SpendingListEvent) -> Unit),
    onClick: ((Spending) -> Unit),
    onLongPress: ((Spending) -> Unit),
) {
    val viewState by state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            ListTopAppBarWidget(
                topAppBarText = stringResource(id = R.string.list_app_bar),
                listItems = getMenuItemsList {
                    onEvent(it)
                }
            )
        },
        content = { contentPadding ->
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()
                    .systemBarsPadding()
                    .padding(contentPadding)
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    if (viewState.listOfSpending.isEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(id = R.string.empty_list_of_spendings),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    } else {
                        LazyColumn {
                            items(viewState.listOfSpending) { spending ->
                                ItemCardWidget(
                                    spending = spending,
                                    onClick = onClick,
                                    onLongPress = onLongPress
                                )
                            }
                        }
                    }
                }
                AddFab(modifier = Modifier
                    .align(Alignment.BottomEnd),
                    onFabClicked = {
                        onEvent(SpendingListEvent.AddNew)
                    })
            }
        })
}

@Preview
@Composable
fun PreviewSpendings() {
    val state = MutableStateFlow(SpendingListState(SampleData.spendingsSample))
    MicroBudgetTheme {
        ListOfSpendingWidget(
            state = state,
            onEvent = {},
            onClick = {},
            onLongPress = {}
        )
    }
}

@Preview
@Composable
fun PreviewSpendingsEmpty() {
    val state = MutableStateFlow(SpendingListState())
    MicroBudgetTheme {
        ListOfSpendingWidget(
            state = state,
            onEvent = {},
            onClick = {},
            onLongPress = {}
        )
    }
}

@Composable
fun AddFab(
    modifier: Modifier = Modifier,
    onFabClicked: () -> Unit = { }
) {
    FloatingActionButton(
        onClick = onFabClicked,
        modifier = modifier
            .padding(20.dp)
            .navigationBarsPadding()
            .height(48.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = stringResource(id = R.string.content_description_add),
            modifier = Modifier.size(28.dp)
        )
    }
}


fun getMenuItemsList(action: ((SpendingListEvent) -> Unit)): ArrayList<MenuItemData> {
    val listItems = ArrayList<MenuItemData>()

    listItems.add(
        MenuItemData(
            textRes = R.string.menu_add,
            icon = Icons.Outlined.Add,
            doAction = { action(SpendingListEvent.AddNew) }
        )
    )
    listItems.add(
        MenuItemData(
            textRes = R.string.menu_delete_all,
            icon = Icons.Outlined.Delete,
            doAction = { action(SpendingListEvent.DeleteAll) }
        )
    )
    listItems.add(
        MenuItemData(
            textRes = R.string.menu_about,
            icon = Icons.Outlined.Info,
            doAction = { action(SpendingListEvent.ShowAbout) }
        )
    )

    return listItems
}


object SampleData {
    // Sample data
    val spendingsSample = listOf(
        Spending(
            date = ZonedDateTime.now(),
            purpose = "grocery",
            amount = 99.4,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now(),
            purpose = "grocery",
            amount = 47.4,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now().minusDays(1),
            purpose = "snacks",
            amount = 55.0,
            currency = Currency.CZECH
        ),
        Spending(
            date = ZonedDateTime.now().minusDays(2),
            purpose = "vacation",
            amount = 500.0,
            currency = Currency.EURO
        ),
    )
}