@file:OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)

package com.wuujcik.microbudget.ui.screens.list

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.IconButton
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootGraph
import com.ramcosta.composedestinations.generated.destinations.TransactionDetailScreenDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.data.entities.Transaction
import com.wuujcik.microbudget.ui.composables.ActionsDropDownMenu
import com.wuujcik.microbudget.ui.composables.TransactionItem
import com.wuujcik.microbudget.ui.dialogs.AboutDialog
import com.wuujcik.microbudget.ui.dialogs.ShowOldSpendingsDialog
import com.wuujcik.microbudget.ui.screens.detail.TransactionDetailScreenArguments
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.coroutines.EmptyCoroutineContext

@Destination<RootGraph>(start = true)
@Composable
fun ListOfTransactionsScreen(
    navigator: DestinationsNavigator,
    viewModel: ListOfTransactionsViewModel = koinViewModel(),
) {

    val context = LocalContext.current
    var aboutDialogVisible by remember { mutableStateOf(false) }
    if (aboutDialogVisible) {
        AboutDialog(
            onNameClicked = { openLinkedIn(context) },
            onDismiss = { aboutDialogVisible = false })
    }

    var showOldSpendingsDialogVisible  by remember { mutableStateOf(false) }
    if (showOldSpendingsDialogVisible) {
        ShowOldSpendingsDialog(
            oldSpendings = viewModel.oldSpendings.collectAsState(
                initial = emptyList(),
                context = EmptyCoroutineContext
            ).value,
            onDismiss = { showOldSpendingsDialogVisible = false })
    }

    ListOfTransactionsContent(
        listOfTransactions = viewModel.transactions.collectAsState(
            initial = emptyList(),
            context = EmptyCoroutineContext
        ).value,
        onTransactionClicked = { item ->
            navigator.navigate(
                direction = TransactionDetailScreenDestination(
                    arguments = TransactionDetailScreenArguments(
                        originalItem = item
                    )
                )
            )
        },
        onCreateNewClicked = {
            navigator.navigate(
                direction = TransactionDetailScreenDestination(
                    arguments = TransactionDetailScreenArguments()
                )
            )
        },
        onDeleteRequested = { viewModel.deleteItem(it) },
        showAboutClicked = { aboutDialogVisible = true },
        onDeleteAllClicked = { viewModel.deleteAllTransactions() },
        showOldSpendings = { showOldSpendingsDialogVisible = true}
    )
}

@Composable
fun ListOfTransactionsContent(
    listOfTransactions: List<Transaction>,
    onTransactionClicked: (Transaction) -> Unit,
    onDeleteRequested: (Transaction) -> Unit,
    onCreateNewClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    showAboutClicked: () -> Unit,
    showOldSpendings: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                title = {
                    Text(
                        text = stringResource(id = R.string.list_app_bar),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    )
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }

                    IconButton(onClick = { showMenu = !showMenu }) {
                        androidx.compose.material.Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    ActionsDropDownMenu(
                        showMenu = showMenu,
                        dismissMenu = { showMenu = false },
                        onCreateNewClicked = onCreateNewClicked,
                        onDeleteAllClicked = onDeleteAllClicked,
                        showAboutClicked = showAboutClicked,
                        showOldSpendings = showOldSpendings
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.height(48.dp),
                onClick = onCreateNewClicked,
                backgroundColor = MaterialTheme.colorScheme.primaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.content_description_add),
                    modifier = Modifier.size(28.dp)
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            if (listOfTransactions.isEmpty()) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.empty_list_of_spendings),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
            } else {
                LazyColumn {
                    items(listOfTransactions.distinctBy { it.id }, key = { it.id }) { transaction ->
                        val dismissState = rememberDismissState(
                            confirmStateChange = {
                                when (it) {
                                    DismissValue.Default -> {}
                                    DismissValue.DismissedToEnd,
                                    DismissValue.DismissedToStart ->
                                        onDeleteRequested(transaction)
                                }
                                false
                            }
                        )

                        SwipeToDismiss(
                            state = dismissState,
                            background = { SwipeBackground(dismissState) },
                            dismissContent = {
                                TransactionItem(
                                    transaction = transaction,
                                    onClick = {
                                        onTransactionClicked(transaction)
                                    },
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SwipeBackground(dismissState: DismissState) {
    val direction = dismissState.dismissDirection ?: return

    val color by animateColorAsState(
        when (dismissState.targetValue) {
            DismissValue.Default -> Color.LightGray
            DismissValue.DismissedToEnd -> Color.Red
            DismissValue.DismissedToStart -> Color.Red
        }
    )
    val alignment = when (direction) {
        DismissDirection.StartToEnd -> Alignment.CenterStart
        DismissDirection.EndToStart -> Alignment.CenterEnd
    }
    val icon = when (direction) {
        DismissDirection.StartToEnd -> Icons.Default.Delete
        DismissDirection.EndToStart -> Icons.Default.Delete
    }
    val scale by animateFloatAsState(
        if (dismissState.targetValue == DismissValue.Default) 0.7f else 1f
    )

    Box(
        Modifier
            .fillMaxSize()
            .background(color)
            .padding(horizontal = 20.dp),
        contentAlignment = alignment
    ) {
        Icon(
            icon,
            contentDescription = stringResource(id = R.string.delete_icon_description),
            modifier = Modifier.scale(scale)
        )
    }
}

private fun openLinkedIn(context: Context) {
    val browserIntent =
        Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.about_url_page)))
    startActivity(context, browserIntent, null)
}

@Preview
@Composable
private fun ListOfTransactionsPreview() {
    MicroBudgetTheme {
        ListOfTransactionsContent(
            listOfTransactions = emptyList(),
            onTransactionClicked = {},
            onCreateNewClicked = {},
            onDeleteRequested = {},
            showAboutClicked = {},
            onDeleteAllClicked = {},
            showOldSpendings = {}
        )
    }
}
