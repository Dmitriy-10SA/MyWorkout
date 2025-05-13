package com.andef.myworkout.design.menu.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType.Companion.PrimaryNotEditable
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.andef.myworkout.design.Black
import com.andef.myworkout.design.DarkGray
import com.andef.myworkout.design.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> UiMenu(
    upText: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    onDismiss: () -> Unit,
    items: List<T>,
    value: String,
    itemToString: (T) -> String,
    onItemClick: (T) -> Unit
) {
    Column {
        Text(text = upText, color = DarkGray)
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(PrimaryNotEditable),
                readOnly = true,
                shape = RoundedCornerShape(10.dp),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Black,
                    unfocusedTextColor = Black,
                    disabledTextColor = Black,
                    focusedLabelColor = Black,
                    disabledContainerColor = White,
                    focusedContainerColor = White,
                    unfocusedContainerColor = White,
                    focusedIndicatorColor = Black,
                    disabledIndicatorColor = Black,
                    unfocusedIndicatorColor = Black
                ),
                value = value,
                onValueChange = {}
            )
            ExposedDropdownMenu(
                expanded = expanded,
                shape = RoundedCornerShape(10.dp),
                containerColor = White,
                border = BorderStroke(
                    width = 1.dp,
                    color = Black
                ),
                onDismissRequest = onDismiss
            ) {
                items.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                itemToString(it),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        onClick = {
                            onItemClick(it)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}