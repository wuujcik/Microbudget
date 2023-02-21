package com.wuujcik.microbudget.ui.presentation.spendingList.widgets

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.wuujcik.microbudget.R
import com.wuujcik.microbudget.ui.presentation.MenuItemData
import com.wuujcik.microbudget.ui.theme.MicroBudgetTheme


@OptIn(ExperimentalMaterial3Api::class) // CenterAlignedTopAppBar is experimental in m3
@Composable
fun ListTopAppBarWidget(topAppBarText: String, listItems: ArrayList<MenuItemData>) {

    // state of the menu
    var expanded by remember {
        mutableStateOf(false)
    }

    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
        title = {
            androidx.compose.material3.Text(
                text = topAppBarText,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.back),
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            // drop down menu
            DropdownMenu(
                modifier = Modifier.width(width = 150.dp),
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                // adjust the position
                offset = DpOffset(x = (-102).dp, y = (-64).dp),
                properties = PopupProperties()
            ) {

                // adding each menu item
                listItems.forEach { menuItemData ->
                    DropdownMenuItem(
                        onClick = {
                            menuItemData.doAction()
                            expanded = false
                        },
                        enabled = true,
                        text = {
                            Text(
                                text = stringResource(menuItemData.textRes),
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = menuItemData.icon,
                                contentDescription = stringResource(menuItemData.textRes),
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewListTopAppBarWidget() {
    MicroBudgetTheme {
        ListTopAppBarWidget(
            topAppBarText = "",
            listItems = ArrayList()
        )
    }
}
