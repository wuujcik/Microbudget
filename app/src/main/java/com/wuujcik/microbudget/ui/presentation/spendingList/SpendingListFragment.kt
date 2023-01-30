package com.wuujcik.microbudget.ui.presentation.spendingList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.Screen
import com.wuujcik.microbudget.ui.navigate
import com.wuujcik.microbudget.ui.presentation.spendingList.widgets.ListOfSpendingWidget
import com.wuujcik.microbudget.ui.presentation.spendingList.widgets.SpendingListEvent
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpendingListFragment : Fragment() {

    private val viewModel: SpendingListViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SpendingList)
            }
        }

        return ComposeView(requireContext()).apply {
            // In order for savedState to work, the same ID needs to be used for all instances.
            id = R.id.spending_list_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                MicroBudgetTheme {
                    ListOfSpendingWidget(
                        state = viewModel.state,
                        onEvent = { event ->
                            when (event) {
                                SpendingListEvent.AddNew -> {
                                    viewModel.addNew()
                                }
                                is SpendingListEvent.Edit -> {
                                    viewModel.edit(event.spending)
                                }
                                is SpendingListEvent.Delete -> {
                                    viewModel.deleteOne(event.spending)
                                }
                            }
                        },
                        onClick = { viewModel.edit(it) },
                        onLongPress = { viewModel.deleteOne(it) } //TODO: make it more obvious for the user that this is deleting
                    )
                }
            }
        }
    }
}