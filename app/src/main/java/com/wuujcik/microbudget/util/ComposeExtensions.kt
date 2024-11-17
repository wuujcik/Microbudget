package com.wuujcik.microbudget.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow


@Composable
fun <T> rememberFlow(
    flow: Flow<T>,
    onEvent: (T) -> Unit
) {
    LaunchedEffect(flow) {
        flow.collect { value ->
            onEvent(value)
        }
    }
}
