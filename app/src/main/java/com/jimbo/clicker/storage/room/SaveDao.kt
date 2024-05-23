package com.jimbo.clicker.storage.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface SaveDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(save: Save)

    @Update
    suspend fun update(save: Save)

    @Delete
    suspend fun delete(save: Save)

    @Query("SELECT * from save WHERE id = 1 LIMIT 1")
    suspend fun getSave(): Save
}