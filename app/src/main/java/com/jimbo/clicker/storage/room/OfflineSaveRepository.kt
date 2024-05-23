package com.jimbo.clicker.storage.room

class OfflineSaveRepository(private val saveDao: SaveDao) : SaveRepository {
    override suspend fun getSave() = saveDao.getSave()
    override suspend fun insertSave(save: Save) = saveDao.insert(save)

    override suspend fun updateSave(save: Save) = saveDao.update(save)

    override suspend fun deleteSave(save: Save) = saveDao.delete(save)
}