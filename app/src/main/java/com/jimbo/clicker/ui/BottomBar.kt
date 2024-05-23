package com.jimbo.clicker.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.ClickerViewModel
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch

/**
 * Composition of the bottom bar
 */
@Composable
fun BottomBar(viewModel: ClickerViewModel) {
    Row(
        modifier = Modifier
            .drawNinePatch(LocalContext.current, R.drawable.bottom_bar_9p)
            .padding(start = 8.dp, end = 8.dp, top = 10.dp, bottom = 8.dp)
    ) {
        CustomButton(
            onClick = { viewModel.updatePanelState(PanelState.SHOW_EQUIPMENT) },
            modifier = Modifier.weight(1f)
        ) { Text(text = stringResource(R.string.equipments)) }
        CustomButton(
            onClick = { viewModel.updatePanelState(PanelState.SHOW_SPIRIT) },
            modifier = Modifier.weight(1f)
        ) { Text(text = stringResource(R.string.spirits)) }
        CustomButton(
            onClick = { viewModel.updatePanelState(PanelState.SHOW_STATS) },
            modifier = Modifier.weight(1f)
        ) { Text(text = stringResource(R.string.stats)) }
    }
}