package com.wuujcik.microbudget.ui.presentation.spendingList

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.presentation.spendingList.widgets.AboutWidget
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
        viewModel.navigateTo.observe(viewLifecycleOwner) { screenEvent ->
            screenEvent.getContentIfNotHandled()?.let { screen ->
                findNavController().navigate(screen.fragmentId, screen.args)
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
                var aboutPopupShown by remember { mutableStateOf(false) }
                if (aboutPopupShown) {
                    AboutWidget(
                        onNameClicked = { openLinkedIn() },
                        onDismiss = { aboutPopupShown = false })
                }

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
                                SpendingListEvent.DeleteAll -> {
                                    viewModel.deleteAll()
                                }
                                SpendingListEvent.ShowAbout -> {
                                    aboutPopupShown = true
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

    private fun openLinkedIn() {
        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.about_url_page)))
        startActivity(browserIntent)
    }
}