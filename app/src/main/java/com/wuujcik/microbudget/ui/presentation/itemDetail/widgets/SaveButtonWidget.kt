package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.wuujcik.data.entities.Spending
import com.wuujcik.microbudget.R


@Composable
fun SaveButtonWidget(
    spending: Spending?,
    onSaveClicked: (spending: Spending) -> Unit,
    isSaveButtonEnabled: Boolean
) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(48.dp),
        shape = MaterialTheme.shapes.small,
        onClick = { spending?.let { onSaveClicked(it) } },
        enabled = isSaveButtonEnabled,
    ) {
        Text(text = stringResource(id = R.string.save))
    }
}