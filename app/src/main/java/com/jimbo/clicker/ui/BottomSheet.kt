package com.jimbo.clicker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.R
import com.jimbo.clicker.util.drawNinePatch
import kotlinx.coroutines.launch

/**
 * Create a [BottomSheet] with the parameters.
 * While using this fonction you must define what onDismissRequest do and what is the "if" for the isVisible boolean
 * @param label Name of the bottom sheet
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    label: String,
    onDismissRequest: () -> Unit,
    isVisible: () -> Boolean,
    content: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    if (isVisible()) {
        ModalBottomSheet(
            onDismissRequest = { onDismissRequest() },
            sheetState = sheetState,
            dragHandle = {},
            shape = CutCornerShape(0.dp),
            containerColor = colorResource(id = R.color.navigation_bar),
            contentColor = Color.White
        ) {
            Box(modifier = Modifier.drawNinePatch(LocalContext.current, R.drawable.bottom_bar_9p)) {
                Column {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp)
                    ) {
                        // Set the title in the UI
                        Text(
                            text = label,
                            color = Color.White,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                        )

                        // Exit button
                        TextButton(
                            onClick = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onDismissRequest()
                                    }
                                }
                            }
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.exit),
                                contentDescription = "",
                                modifier = Modifier.size(35.dp)
                            )
                        }
                    }
                    CuteHorizontalDivider()
                    // Content
                    Column(
                        modifier = Modifier.padding(
                            start = 10.dp,
                            end = 10.dp,
                            top = 5.dp,
                            bottom = 60.dp
                        )
                    ) {
                        content()
                    }
                }

            }
        }

    }
}

// Preview does not work without interactive mode
@Preview
@Composable
fun PreviewBottomSheet(function: @Composable () -> Unit) {
    BottomSheet(label = "Preview", onDismissRequest = {}, isVisible = { true }) {
        Text(text = "This is a preview text")
    }
}