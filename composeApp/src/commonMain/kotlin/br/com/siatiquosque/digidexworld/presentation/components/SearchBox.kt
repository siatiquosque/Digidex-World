package br.com.siatiquosque.digidexworld.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun SearchBox(
    value: String,
    onValueChange: (String) -> Unit,
    onSearchExecute: () -> Unit,
    onCleanSearch: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 8.dp)
            .height(55.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
            modifier = Modifier.padding(8.dp).size(30.dp)
        )
        TextField(
            placeholder = {
                Text(text = "Search...")
            },
            value = value,
            onValueChange = {
                onValueChange(it)
                onSearchExecute()
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                keyboardType = KeyboardType.Text,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                },
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxHeight().fillMaxWidth(.8f)
        )

        Icon(
            imageVector = Icons.Filled.Close,
            null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(8.dp).size(30.dp).clickable {
                onValueChange("")
                onSearchExecute()
                onCleanSearch()
                keyboardController?.hide()
            },
        )
    }
}