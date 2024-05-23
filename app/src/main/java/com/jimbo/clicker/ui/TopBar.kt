package com.jimbo.clicker.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.ClickerViewModel
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch

/**
 * How the TopBar is composed.
 * @param viewModel What is the [viewModel] it should use
 */
@Composable
fun TopBar(viewModel: ClickerViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .drawNinePatch(LocalContext.current, R.drawable.top_bar_9p)
    ) {
        val state = viewModel.uiState.collectAsState().value
        if (state is ClickerViewModel.State.Data) {
            Score( // Show the current score
                score = state.score,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 100.dp)
            )
        }
        val interactionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        val rotationAnimation: Float by animateFloatAsState(
            if (isPressed) 90f else 0f,
            label = "rotation"
        )
        TextButton( // Setting button
            interactionSource = interactionSource,
            modifier = Modifier
                .padding(5.dp)
                .size(75.dp)
                .align(Alignment.TopEnd),
            onClick = {
                viewModel.updatePanelState(PanelState.SHOW_SETTINGS)
            }
        ) {
            Image(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = R.string.settings.toString(),
                modifier = Modifier.rotate(rotationAnimation)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopBar() {
    TopBar(viewModel = ClickerViewModel())
}