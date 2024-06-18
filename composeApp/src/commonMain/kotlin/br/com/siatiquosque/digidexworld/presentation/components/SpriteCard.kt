package br.com.siatiquosque.digidexworld.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SpriteCard(
    sprite: DrawableResource,
    name: String
) {
    Row {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painterResource(sprite),
            contentDescription = name
        )
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = name
        )
    }
}