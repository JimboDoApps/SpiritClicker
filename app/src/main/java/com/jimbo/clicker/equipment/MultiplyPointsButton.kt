package com.jimbo.clicker.equipment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.ClickerViewModel
import com.jimbo.clicker.R
import com.jimbo.clicker.ui.CustomButton

/**
 * Create the multiply button base (Not used for now)
 * @param cost Bonus cost
 * @param multiplier the new multiplier you obtain after buying it
 * @param state collect the last MVVM state
 */
@Composable
private fun MultiplyPointsButton(
    cost: Int,
    multiplier: Int,
    state: ClickerViewModel.State.Data,
    onClick: (Int, Int) -> Unit
) {
    val enabled = state.multiplier < multiplier && state.score > cost - 1
    CustomButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(cost, multiplier) },
        enabled = enabled
    ) {
        Column {
            Row {
                Text(text = "x$multiplier")
                Image(
                    painter = painterResource(id = R.drawable.essence),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
                Text(text = "/Click")
            }
            Row {
                Text(text = cost.toString())
                Image(
                    painter = painterResource(id = R.drawable.essence),
                    contentDescription = "",
                    modifier = Modifier.size(15.dp)
                )
            }
        }
    }
}