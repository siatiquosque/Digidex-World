package br.com.siatiquosque.digidexworld.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import br.com.siatiquosque.digidexworld.presentation.theme.DigiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    titleToolbar: String? = null,
    toolbar: @Composable (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable (RowScope.() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            navigationIcon = {
                onBack?.let {
                    IconButton(
                        onClick = onBack
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "ArrowBack",
                        )
                    }
                }
            },
            actions = {
                actions?.invoke(this)
            },
            title = {
                Row {
                    toolbar?.let {
                        it.invoke()
                    } ?: run {
                        titleToolbar?.let {
                            Text(
                                text = titleToolbar,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                    }
                }
            }
        )
    }) {
        Box(
            modifier = Modifier.padding(top = it.calculateTopPadding())
                .fillMaxSize()
                .background(DigiTheme.colors.Background),
        ) {
            content()
        }
    }
}