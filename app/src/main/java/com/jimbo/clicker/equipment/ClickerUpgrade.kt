package com.jimbo.clicker.equipment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.R
import com.jimbo.clicker.storage.IStorage
import com.jimbo.clicker.ui.CustomButton
import com.jimbo.clicker.util.drawNinePatch
import com.jimbo.clicker.util.numberFormat

/**
 * Main upgrade for the essence revenue per click
 * @param storedUpgrade Is the database item used for this upgrade
 * @param currentScore Needed to know how much essence is needed to upgrade
 */
@Composable
fun ClickerUpgrade(
    storedUpgrade: IStorage.StoredUpgrade,
    currentScore: Double,
    onClick: (IStorage.StoredUpgrade) -> Unit
) {
    Column(
        modifier = Modifier
            .drawNinePatch(LocalContext.current, R.drawable.clicker_upgrade_9p)
            .padding(8.dp)
            .wrapContentSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Info Button
                    TextButton(
                        onClick = { } // TODO
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = "info"
                        )
                    }
                    // Title
                    Text(text = storedUpgrade.title)
                }
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    // Level
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.level, storedUpgrade.level)
                    )
                    // Revenue of the equipment
                    Text(
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Left,
                        text = stringResource(
                            R.string.revenue_per_click,
                            storedUpgrade.power.numberFormat()
                        )
                    )
                }
            }
            // Upgrade button with price
            CustomButton(
                modifier = Modifier
                    .padding(8.dp)
                    .size(80.dp),
                onClick = { onClick(storedUpgrade) },
                enabled = currentScore >= storedUpgrade.cost
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = "upgrade"
                    )
                    Text(
                        textAlign = TextAlign.Center,
                        text = storedUpgrade.cost.numberFormat()
                    )
                }

            }
        }
        // Progress bar
        LinearProgressIndicator(
            progress = { (currentScore / storedUpgrade.cost).toFloat() },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewClickerUpgrade() {
    ModalBottomSheet(onDismissRequest = { }) {
        ClickerUpgrade(
            storedUpgrade = IStorage.StoredUpgrade(
                id = 1,
                title = "Spirit hand",
                description = "",
                level = 0,
                power = 0.0,
                cost = 50.0
            ),
            10.0,
            onClick = {}
        )
    }
}