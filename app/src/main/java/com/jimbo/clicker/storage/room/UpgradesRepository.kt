package com.jimbo.clicker.storage.room

/**
 * Repository that provides update, delete, and retrieve of [Upgrade] from a given data source.
 */
interface UpgradesRepository {
    /**
     * Insert an upgrade in the data source
     */
    suspend fun insertUpgrades(upgrade: Upgrade)

    /**
     * Retrieve all the items from the given data source.
     */
    suspend fun getUpgrades(): List<Upgrade>
    suspend fun getUpgrade(id: Int): Upgrade

    /**
     * Update save in the data source
     */
    suspend fun updateUpgrade(target: Upgrade)

    /**
     * delete save in the data source
     */
    suspend fun deleteUpgrade(target: Upgrade)
}