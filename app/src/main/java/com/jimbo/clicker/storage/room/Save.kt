package com.jimbo.clicker.storage.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "save")
data class Save(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val score: Double = 0.0,
    val multiplier: Int = 1,
    val statClickCount: Long = 0,
    val statEssenceCount: Double = 0.0
)
