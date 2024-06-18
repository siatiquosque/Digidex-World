package br.com.siatiquosque.digidexworld.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import br.com.siatiquosque.digidex_shared.data.model.dw1.DigimonEntity
import br.com.siatiquosque.digidexworld.presentation.theme.DigiTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageCard(image: DrawableResource, contentDescription: String) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, DigiTheme.colors.PrimaryOrangeAgumon)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth(),
                painter = painterResource(
                    image
                ),
                contentDescription = contentDescription,
                contentScale = ContentScale.FillWidth,

                )
        }
    }
}