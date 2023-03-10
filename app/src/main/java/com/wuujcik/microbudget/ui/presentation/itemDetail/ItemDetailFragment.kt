package com.wuujcik.microbudget.ui.presentation.itemDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.presentation.itemDetail.widgets.ItemDetailWidget
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme
import com.wuujcik.microbudget.util.toMilliseconds
import com.wuujcik.microbudget.util.toZonedDateTime
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ItemDetailFragment : Fragment() {

    private val args: ItemDetailFragmentArgs by navArgs()

    private val viewModel: ItemDetailViewModel by viewModel { parametersOf(args.item) }

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
            id = R.id.item_detail_fragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            setContent {
                MicroBudgetTheme {
                    ItemDetailWidget(
                        originalItem = viewModel.originalItem,
                        onBackPressed = { activity?.onBackPressedDispatcher?.onBackPressed() },
                        onDateClicked = ::showDatePicker,
                        onCurrencyClicked = {
                            viewModel.onCurrencyChanged(it)
                        },
                        onSaveClicked = { spending ->
                            viewModel.onSaveClicked(spending)
                        },
                        currencies = viewModel.availableCurrencies,
                        dateTime = viewModel.chosenDate,
                        chosenCurrency = viewModel.chosenCurrency,
                    )
                }
            }
        }
    }

    private fun showDatePicker() {
        val date = viewModel.chosenDate
        val picker = MaterialDatePicker.Builder.datePicker()
            .setSelection(date.toMilliseconds())
            .build()
        picker.show(requireActivity().supportFragmentManager, picker.toString())
        picker.addOnPositiveButtonClickListener {
            picker.selection?.let { selectedDate ->
                viewModel.onDateChosen(selectedDate.toZonedDateTime())
            }
        }
    }
}
