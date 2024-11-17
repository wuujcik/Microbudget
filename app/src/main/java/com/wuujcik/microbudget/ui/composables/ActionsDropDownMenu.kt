package com.wuujcik.microbudget.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.wuujcik.microbudget.R

@Composable
fun ActionsDropDownMenu(
    showMenu: Boolean,
    dismissMenu: () -> Unit,
    onCreateNewClicked: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    showAboutClicked: () -> Unit
) {
    DropdownMenu(
        expanded = showMenu,
        onDismissRequest = dismissMenu,
        modifier = Modifier.background( MaterialTheme.colorScheme.onSecondary)
    ) {
        DropdownMenuItem(
            onClick = {
                onCreateNewClicked()
                dismissMenu()
            }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(id = R.string.menu_add),
                modifier = Modifier.padding(start = 8.dp),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        DropdownMenuItem(
            onClick = {
                onDeleteAllClicked()
                dismissMenu()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(id = R.string.menu_delete_all),
                modifier = Modifier.padding(start = 8.dp),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
        DropdownMenuItem(
            onClick = {
                showAboutClicked()
                dismissMenu()
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface,
            )
            Text(
                text = stringResource(id = R.string.menu_about),
                modifier = Modifier.padding(start = 8.dp),
                textAlign = TextAlign.Left,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}
