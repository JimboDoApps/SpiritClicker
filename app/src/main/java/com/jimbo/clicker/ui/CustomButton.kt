package com.jimbo.clicker.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch

/**
 * Custom button base with regular, pressed and disabled state made with the custom 9patch backgrounds
 */
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = MaterialTheme.shapes.small,
    border: BorderStroke? = null,
    content: @Composable RowScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val backgroundImg = getButtonBackground(isPressed, enabled)

    val color = if (isPressed) R.color.white else R.color.text_onButton

    Button(
        onClick = { onClick() },
        interactionSource = interactionSource,
        shape = shape,
        border = border,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(contentColor = colorResource(id = color)),
        elevation = null,
        contentPadding = PaddingValues(10.dp),
        modifier = modifier
            .drawNinePatch(LocalContext.current, backgroundImg)
            .padding(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                content = content
            )
        }
    }
}

private fun getButtonBackground(isPressed: Boolean, enabled: Boolean) = when {
    isPressed -> R.drawable.button_pressed_9p
    !enabled -> R.drawable.button_disabled_9p
    else -> R.drawable.button_9p
}

@Preview
@Composable
fun PreviewCustomButton() {
    Column {
        CustomButton(onClick = { }, enabled = false) {
            Text(text = "Disabled Button")
        }
        CustomButton(onClick = { }) {
            Text(text = "Regular Button")
        }
    }

}