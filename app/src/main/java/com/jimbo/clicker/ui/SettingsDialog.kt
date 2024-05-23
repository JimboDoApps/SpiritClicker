package com.jimbo.clicker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch

/**
 *  Construction of the settings [Dialog].
 *  You must define what the onDismissRequest does to be able to exit the dialog by clicking outside the box or on the exit button
 */
@Composable
fun SettingsDialog(onDismissRequest: () -> Unit, onResetData: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(20.dp)
                .drawNinePatch(LocalContext.current, R.drawable.dialog_9p),
            shape = CutCornerShape(0.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp)
            ) {
                // Make the title of the dialog appear
                Text(
                    text = stringResource(R.string.settings),
                    color = Color.White,
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f)
                )
                // Exit button
                TextButton(onClick = { onDismissRequest() }) {
                    Image(
                        painter = painterResource(id = R.drawable.exit),
                        contentDescription = "",
                        modifier = Modifier.size(35.dp)
                    )
                }
            }

            CustomButton(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp),
                onClick = { onResetData() }) {
                Text(text = stringResource(R.string.reset_data))
            }
            Spacer(modifier = Modifier.height(5.dp))

            Text(text = stringResource(R.string.copyright))
            Text(text = stringResource(R.string.app_version))
        }
    }
}

@Preview
@Composable
fun PreviewSettingsDialog() {
    SettingsDialog(onDismissRequest = {}, onResetData = {})
}