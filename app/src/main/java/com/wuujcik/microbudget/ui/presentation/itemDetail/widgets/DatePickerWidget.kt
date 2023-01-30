package com.wuujcik.microbudget.ui.presentation.itemDetail.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.wuujcik.data.DateTimeFormatter.toEuropeanDateFormat
import java.time.ZonedDateTime


@Composable
fun DatePickerWidget(
    date: ZonedDateTime = ZonedDateTime.now(),
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
                .copy(alpha = 0.87f),
        ),
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(54.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)),
    ) {
        Text(text = date.toEuropeanDateFormat())
        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
        )
    }
}