package com.jimbo.clicker.equipment

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import com.jimbo.clicker.storage.IStorage

/**
 * Export of the list of upgrade buttons
 */
@Composable
fun Equipments(
    upgrades: List<IStorage.StoredUpgrade>,
    currentScore: Double,
    onClick: (IStorage.StoredUpgrade) -> Unit
) {
    LazyColumn {
        items(upgrades) {
            ClickerUpgrade(it, currentScore, onClick)
        }
    }
}