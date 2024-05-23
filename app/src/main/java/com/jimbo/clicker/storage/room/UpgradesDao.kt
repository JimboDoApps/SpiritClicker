package com.jimbo.clicker.storage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UpgradesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(upgrade: Upgrade)

    @Update
    suspend fun update(target: Upgrade)

    @Delete
    suspend fun delete(target: Upgrade)

    @Query("SELECT * from upgrades")
    suspend fun getUpgrades(): List<Upgrade>

    @Query("SELECT * from upgrades WHERE id = :id")
    suspend fun getUpgrade(id: Int): Upgrade
}