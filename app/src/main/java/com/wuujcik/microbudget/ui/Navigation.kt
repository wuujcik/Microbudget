package com.wuujcik.microbudget.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.wuujcik.microbudget.R
import java.security.InvalidParameterException

enum class Screen { SpendingList, ItemDetail }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) {
        throw InvalidParameterException("Can't navigate to $to")
    }
    when (to) {
        Screen.SpendingList -> {
            findNavController().navigate(R.id.spending_list_fragment)
        }
        Screen.ItemDetail -> {
            findNavController().navigate(R.id.item_detail_fragment)
        }
    }
}
