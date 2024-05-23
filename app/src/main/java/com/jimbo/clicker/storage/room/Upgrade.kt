package com.jimbo.clicker.storage.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upgrades")
data class Upgrade(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val level: Int = 0,
    val power: Double,
    val cost: Double
)