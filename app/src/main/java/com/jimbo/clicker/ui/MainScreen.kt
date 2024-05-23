package com.jimbo.clicker.ui

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jimbo.clicker.ClickerViewModel
import com.jimbo.clicker.R
import com.jimbo.clicker.equipment.Equipments
import com.jimbo.clicker.ui.theme.ClickerTheme
import com.jimbo.clicker.util.numberFormat
import com.jimbo.clicker.util.pxToDp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * Screen base of our app
 * @param viewModel Collect the data and the states of the app with our [viewModel]
 */
@Composable
fun MainScreen(viewModel: ClickerViewModel = ClickerViewModel()) {
    ClickerTheme {
        Scaffold(
            topBar = { TopBar(viewModel) },
            bottomBar = { BottomBar(viewModel) }) { paddingValues ->
            Surface(
                modifier = Modifier
                    .padding(
                        start = paddingValues.calculateStartPadding(LocalLayoutDirection.current),
                        end = paddingValues.calculateEndPadding(LocalLayoutDirection.current),
                        // in order to remove the space between the bottom of the TopBar and the content:
                        top = paddingValues.calculateTopPadding() - 8.dp,
                        bottom = paddingValues.calculateBottomPadding()
                    )
                    .fillMaxSize()
                    .paint(
                        // Background of the app
                        painter = painterResource(id = R.drawable.background),
                        contentScale = ContentScale.FillBounds
                    )
                    .pointerInput(Unit) {
                        // Allow us to click and collect the position of the click on the screen
                        detectTapGestures(onPress = {
                            viewModel.hasClicked(it)
                        })
                    },
                color = Color.Transparent
            ) {
                Box {
                    // listen to any uiState changes
                    // with each new state collected, the app goes back in the 'when'
                    when (val state = viewModel.uiState.collectAsState().value) {
                        is ClickerViewModel.State.Data -> ScoreScreen(viewModel, state)

                        ClickerViewModel.State.Loading -> {
                            // does nothing
                        }
                    }
                }
            }
        }
    }
}

/**
 * Extraction of the construction of the app to be a separated function
 * @param state Collecting the data aith our MVVM State
 */
@Composable
private fun ScoreScreen(viewModel: ClickerViewModel, state: ClickerViewModel.State.Data) {
    // To show the score when you click : the Scoretext function receive the offset of the click and show the text + the animation
    state.clicks.forEach { offset ->
        ScoreText(offset, state) { viewModel.removeClick() }
    }

    Dialogs(state, viewModel)
    BottomSheets(state, viewModel)
}

/**
 * Gathering of all the possible dialogs the app can show
 */
@Composable
private fun Dialogs(
    state: ClickerViewModel.State.Data,
    viewModel: ClickerViewModel
) {
    when (state.panelState) {
        // Settings dialog
        PanelState.SHOW_SETTINGS -> {
            SettingsDialog(
                onDismissRequest = { viewModel.updatePanelState(PanelState.HIDDEN) },
                onResetData = { viewModel.updatePanelState(PanelState.SHOW_ALERT_RESET) }
            )
        }
        // Alert Reset Data
        PanelState.SHOW_ALERT_RESET -> {
            BaseAlertDialog(
                icon = Icons.Default.Info,
                dialogTitle = stringResource(R.string.reset_data),
                dialogText = stringResource(R.string.reset_alert_text),
                onDismissRequest = { viewModel.updatePanelState(PanelState.SHOW_SETTINGS) },
                onConfirm = { viewModel.resetData() }
            )
        }

        else -> {
            // Do nothing
        }
    }
}

/**
 * Construction of the bottoms sheets
 * @param state Collect the data from the MVVM State
 * @param viewModel Which viewModel is used
 */
@Composable
private fun BottomSheets(
    state: ClickerViewModel.State.Data,
    viewModel: ClickerViewModel
) {
    // Equipment list
    BottomSheet(
        label = stringResource(id = R.string.equipments),
        onDismissRequest = { viewModel.updatePanelState(panelState = PanelState.HIDDEN) },
        isVisible = { state.panelState == PanelState.SHOW_EQUIPMENT }
    ) {
        Equipments(state.upgrades, state.score, viewModel::hasClickedUpgrade)
    }
    // Spirit list
    BottomSheet(
        label = stringResource(id = R.string.spirits),
        onDismissRequest = { viewModel.updatePanelState(panelState = PanelState.HIDDEN) },
        isVisible = { state.panelState == PanelState.SHOW_SPIRIT }
    ) {
        // TODO: Temporary text -> Add the spirit management
        Text(text = "This is a spirit list. Indeed")
    }
    // Stats
    BottomSheet(
        label = stringResource(id = R.string.stats),
        onDismissRequest = { viewModel.updatePanelState(panelState = PanelState.HIDDEN) },
        isVisible = { state.panelState == PanelState.SHOW_STATS }
    ) {
        Text(text = stringResource(R.string.click_number, state.totalClicks))
        CuteHorizontalDivider(Modifier.alpha(0.5f))
        Row {
            Text(text = stringResource(R.string.essence_count, state.totalEssences.numberFormat()))
            Image(
                painter = painterResource(id = R.drawable.essence),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

/**
 * Show the score gain when you click
 * @param offset collect the position of the click
 * @param state collect the last MVVM State
 */
@Composable
private fun ScoreText(
    offset: Offset,
    state: ClickerViewModel.State.Data,
    onAnimationEnd: () -> Unit
) {
    val alphaAnimation = remember { Animatable(1f) }
    AnimClick(offset = offset, alphaAnimation)
    Row(modifier = Modifier
        .absoluteOffset(offset.x.pxToDp(), offset.y.pxToDp())
        .graphicsLayer { alpha = alphaAnimation.value }) {
        Text(
            text = "+ ${(state.power * state.multiplier).numberFormat()}",
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
        )
        Column {
            Spacer(modifier = Modifier.height(5.dp))
            Image(
                painter = painterResource(id = R.drawable.essence),
                contentDescription = "",
                modifier = Modifier.size(20.dp)
            )
        }
    }
    LaunchedEffect(offset) {
        alphaAnimation.animateTo(0f, animationSpec = tween(1000))
        onAnimationEnd()
    }
}

// Preview
@Preview
@SuppressLint("StaticFieldLeak")
@Composable
fun MainScreenPreview() {
    val viewModel = object : ClickerViewModel() {
        override val uiState: StateFlow<State>
            get() = MutableStateFlow(
                State.Data(
                    score = 400.0,
                    multiplier = 2,
                    totalClicks = 210,
                    totalEssences = 610.0
                )
            )
    }
    MainScreen(viewModel)
}