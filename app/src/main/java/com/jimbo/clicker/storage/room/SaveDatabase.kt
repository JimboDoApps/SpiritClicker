package com.jimbo.clicker.storage.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [Save::class, Upgrade::class], version = 1, exportSchema = false)
abstract class SaveDatabase : RoomDatabase() {
    abstract fun saveDao(): SaveDao
    abstract fun upgradesDao(): UpgradesDao

    companion object {
        @Volatile
        private var Instance: SaveDatabase? = null
        private const val DATABASE_NAME = "save_database"

        fun getDatabase(context: Context): SaveDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, SaveDatabase::class.java, DATABASE_NAME)
                    .createFromAsset("save_database.db")
                    .build()
                    .also { Instance = it }
            }
        }

        fun deleteDatabase(context: Context) {
            context.deleteDatabase(DATABASE_NAME)
            Instance = null
        }
    }
}