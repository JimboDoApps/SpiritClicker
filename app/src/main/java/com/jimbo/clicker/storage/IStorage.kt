package com.jimbo.clicker.storage

interface IStorage {
    // [Save]
    suspend fun getScore(): Double
    suspend fun setScore(score: Double)
    suspend fun getMultiplier(): Int
    suspend fun setMultiplier(multiplier: Int)
    suspend fun getStatClickCount(): Long
    suspend fun setStatClickCount(statClickCount: Long)
    suspend fun getStatEssenceCount(): Double
    suspend fun setStatEssenceCount(statEssenceCount: Double)


    // [Upgrade]
    suspend fun getUpgrades(): List<StoredUpgrade>
    suspend fun getUpgrade(id: Int): StoredUpgrade
    suspend fun setUpgrade(target: StoredUpgrade)

    suspend fun reset()

    /**
     * Data class for storing all the infos of an upgrade taken from the database
     */
    data class StoredUpgrade(
        val id: Int,
        val title: String,
        val description: String,
        val level: Int,
        val power: Double,
        val cost: Double
    )
}
