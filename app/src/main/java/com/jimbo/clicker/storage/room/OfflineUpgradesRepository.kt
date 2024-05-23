package com.jimbo.clicker.storage.room

class OfflineUpgradesRepository(private val upgradesDao: UpgradesDao) : UpgradesRepository {
    override suspend fun getUpgrades() = upgradesDao.getUpgrades()
    override suspend fun getUpgrade(id: Int) = upgradesDao.getUpgrade(id)
    override suspend fun insertUpgrades(upgrade: Upgrade) = upgradesDao.insert(upgrade)

    override suspend fun updateUpgrade(target: Upgrade) = upgradesDao.update(target)
    override suspend fun deleteUpgrade(target: Upgrade) = upgradesDao.delete(target)
}