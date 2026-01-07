package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Stable
fun interface ItemRenderer {
    @Composable
    operator fun invoke(text: String, selected: Boolean, onClick: () -> Unit)
}

@Composable
fun ItemList(items: List<String>, itemRenderer: ItemRenderer) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }

    Column(Modifier.safeDrawingPadding()) {
        items.forEachIndexed { index, item ->
            itemRenderer(
                text = item,
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            )
        }
    }
}

@Composable
fun App() {
    ItemList(
        items = listOf("Item 0", "Item 1", "Item 2"),
        itemRenderer = { text, selected, onClick ->
            Card(
                modifier = Modifier.clickable(onClick = onClick),
                border = BorderStroke(1.dp, if (selected) Color.Green else Color.Gray)
            ) {
                Text(text = text, modifier = Modifier.padding(16.dp).fillMaxWidth())
            }
        }
    )
}