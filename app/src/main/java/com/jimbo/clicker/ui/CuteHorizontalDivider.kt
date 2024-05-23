package com.jimbo.clicker.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CuteHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        color = Color.White,
        modifier = modifier
            .height(1.dp)
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    )
}

