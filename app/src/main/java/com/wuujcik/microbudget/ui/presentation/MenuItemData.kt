package com.wuujcik.microbudget.ui.presentation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class MenuItemData(
    @StringRes val textRes: Int,
    val icon: ImageVector,
    val doAction: (() -> Unit)
) {
    // allow 'calling' the action like a function
    operator fun invoke() = doAction()
}