package com.jimbo.clicker.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch

/**
 * [AlertDialog] base that can be reused when you call the function.
 * You must define onDismissRequest to be able to leave the alert by clicking outside of the box or on the dismiss button.
 * You also need to define the onConfirmation to set what the confirm button does.
 * @param dialogTitle title of the alert
 * @param dialogText explanation text of the alert
 * @param icon use Icons.Default library for material icons
 */
@Composable
fun BaseAlertDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        containerColor = Color.Transparent,
        modifier = Modifier.drawNinePatch(LocalContext.current, R.drawable.dialog_9p),
        icon = { Icon(icon, contentDescription = "") },
        iconContentColor = Color.White,
        title = { Text(text = dialogTitle) },
        titleContentColor = Color.White,
        text = { Text(text = dialogText) },
        textContentColor = Color.White,
        onDismissRequest = { onDismissRequest() },
        confirmButton = {
            CustomButton(onClick = { onConfirm() }) {
                Text(text = stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            CustomButton(onClick = { onDismissRequest() }) {
                Text(text = stringResource(R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
fun PreviewBaseAlertDialog() {
    BaseAlertDialog(
        onDismissRequest = {  },
        onConfirm = { },
        dialogTitle = "Alert Title",
        dialogText = "Are you sure about doing that decision ?",
        icon = Icons.Default.Info
    )
}