package com.jimbo.clicker.storage.room

/**
 * Repository that provides update, delete, and retrieve of [Save] from a given data source.
 */
interface SaveRepository {
    /**
     * Insert a save in the data source
     */
    suspend fun insertSave(save: Save)

    /**
     * Retrieve all the items from the given data source.
     */
    suspend fun getSave(): Save?

    /**
     * Update save in the data source
     */
    suspend fun updateSave(save: Save)

    /**
     * delete save in the data source
     */
    suspend fun deleteSave(save: Save)
}