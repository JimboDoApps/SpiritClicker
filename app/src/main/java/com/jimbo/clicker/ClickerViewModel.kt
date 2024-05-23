package com.jimbo.clicker

import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jimbo.clicker.storage.IStorage
import com.jimbo.clicker.ui.PanelState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Creation of a [ViewModel] to be in MVVM format
 */
open class ClickerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<State>(State.Loading)
    open val uiState = _uiState.asStateFlow()
    private var clickCount = 0

    // We set the preferences for later (lateinit)
    private lateinit var storage: IStorage

    fun setStorage(storage: IStorage) {
        this.storage = storage
        if (_uiState.value is State.Data) return
        // Creation of a state to collect data from the database
        viewModelScope.launch {
            val upgrades = storage.getUpgrades()
            setData(
                storage.getScore(),
                upgrades.first().power,
                storage.getMultiplier(),
                storage.getStatClickCount(),
                storage.getStatEssenceCount(),
                upgrades
            )
        }
    }

    private fun setData(
        score: Double,
        power: Double,
        multiplier: Int,
        totalClicks: Long,
        totalEssences: Double,
        upgrades: List<IStorage.StoredUpgrade>
    ) {
        viewModelScope.launch {
            _uiState.emit(
                State.Data(
                    score = score,
                    power = power,
                    multiplier = multiplier,
                    totalClicks = totalClicks,
                    totalEssences = totalEssences,
                    upgrades = upgrades
                )
            )
        }
    }

    // MVVM version of the clic function
    fun hasClicked(offset: Offset) {
        clickCount++
        viewModelScope.launch {
            _uiState.update { state ->
                state as State.Data
                val score = state.score + (state.power * state.multiplier)
                val totalEssences = storage.getStatEssenceCount() + (state.power * state.multiplier)
                storage.setScore(score)
                storage.setStatEssenceCount(totalEssences)
                val clicks = state.clicks.toMutableList().apply { add(offset) }
                val statClickCount = storage.getStatClickCount() + 1
                storage.setStatClickCount(statClickCount)
                state.copy(
                    score = score,
                    clicks = clicks,
                    totalClicks = statClickCount,
                    totalEssences = totalEssences
                )
            }
        }
    }

    // Show the bottom sheet or dialog needed
    fun updatePanelState(panelState: PanelState) {
        viewModelScope.launch {
            _uiState.update { state ->
                state as State.Data
                state.copy(panelState = panelState)
            }
        }
    }

    // Bonus buttons (Not used for now)
    fun hasClickedMultiplier(cost: Int, multiplier: Int) {
        viewModelScope.launch {
            _uiState.update { state ->
                state as State.Data
                val score = state.score - cost
                storage.setMultiplier(multiplier)
                state.copy(score = score, multiplier = multiplier)
            }
        }
    }

    fun hasClickedUpgrade(storedUpgrade: IStorage.StoredUpgrade) {
        viewModelScope.launch {
            _uiState.update { state ->
                state as State.Data
                val score = state.score - storedUpgrade.cost
                storage.setUpgrade(
                    storedUpgrade.copy(
                        level = storedUpgrade.level + 1,
                        cost = storedUpgrade.cost * 1.30,
                        power = storedUpgrade.power + 0.5
                    )
                )
                state.copy(
                    score = score,
                    power = state.power + 0.5,
                    upgrades = storage.getUpgrades()
                ) // How to improve performances: take upgrades from the existing state, transform it to a MutableListOf, take our storedUpgrade and search his ID to update only its line
            }
        }
    }

    // Function that 'fix' the visual glitch when the score that appeared when you click must be deleted from the mobile data
    fun removeClick() {
        clickCount--
        viewModelScope.launch {
            _uiState.update { state ->
                state as State.Data
                if (clickCount != 0 && state.clicks.size < 10_000) return@update state
                // All the animations are done or the list is full
                clickCount = 0
                val clicks = state.clicks.toMutableList().apply { clear() }
                state.copy(clicks = clicks)
            }
        }
    }

    // Reset all the data
    fun resetData() {
        viewModelScope.launch {
            _uiState.emit(State.Loading)
            storage.reset()
            clickCount = 0
            setStorage(storage)
        }
    }

    // definition of my 'state' (my model)
    sealed class State {
        data object Loading : State()
        data class Data(
            val score: Double = 0.0,
            val power: Double = 1.0,
            val multiplier: Int = 1,
            val panelState: PanelState = PanelState.HIDDEN,
            val clicks: List<Offset> = emptyList(),
            val totalClicks: Long = 0,
            val totalEssences: Double = 0.0,
            val upgrades: List<IStorage.StoredUpgrade> = emptyList()
        ) : State()
    }
}

