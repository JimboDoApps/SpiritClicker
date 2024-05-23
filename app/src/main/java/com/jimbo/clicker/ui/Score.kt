package com.jimbo.clicker.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.R
import com.jimbo.clicker.util.numberFormat

/**
 * Make the score appear. If you do a Modifier on the function call, it is by default modifying the [Row] where the score is.
 * @param score Score to show
 */
@Composable
fun Score(score: Double, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            text = score.numberFormat(),
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = TextUnit(40f, TextUnitType.Sp),
            modifier = Modifier.padding(all = 12.dp)
        )
        Column {
            Spacer(modifier = Modifier.height(25.dp))
            Image(
                painter = painterResource(id = R.drawable.essence),
                contentDescription = "",
                modifier = Modifier.size(32.dp)
            )
        }
    }
}
