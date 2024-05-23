package com.jimbo.clicker.storage.room

import android.annotation.SuppressLint
import android.content.Context
import com.jimbo.clicker.storage.IStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseStorage(private val context: Context) : IStorage {
    private val saveDatabase: SaveDatabase
        get() = SaveDatabase.getDatabase(context)
    private val saveRepository: SaveRepository
        get() = OfflineSaveRepository(saveDatabase.saveDao())
    private val upgradesRepository: UpgradesRepository
        get() = OfflineUpgradesRepository(saveDatabase.upgradesDao())

    override suspend fun getScore() =
        withContext(Dispatchers.IO) { saveRepository.getSave()?.score ?: 0.0 }

    override suspend fun setScore(score: Double) = withContext(Dispatchers.IO) {
        val save = saveRepository.getSave() ?: return@withContext
        saveRepository.updateSave(save.copy(score = score))
    }

    override suspend fun getMultiplier() =
        withContext(Dispatchers.IO) { saveRepository.getSave()?.multiplier ?: 1 }

    override suspend fun setMultiplier(multiplier: Int) = withContext(Dispatchers.IO) {
        val save = saveRepository.getSave() ?: return@withContext
        saveRepository.updateSave(save.copy(multiplier = multiplier))
    }

    override suspend fun getStatClickCount() =
        withContext(Dispatchers.IO) { saveRepository.getSave()?.statClickCount ?: 0 }

    override suspend fun setStatClickCount(statClickCount: Long) = withContext(Dispatchers.IO) {
        val save = saveRepository.getSave() ?: return@withContext
        saveRepository.updateSave(save.copy(statClickCount = statClickCount))
    }

    override suspend fun getStatEssenceCount() =
        withContext(Dispatchers.IO) { saveRepository.getSave()?.statEssenceCount ?: 0.0 }

    override suspend fun setStatEssenceCount(statEssenceCount: Double) =
        withContext(Dispatchers.IO) {
            val save = saveRepository.getSave() ?: return@withContext
            saveRepository.updateSave(save.copy(statEssenceCount = statEssenceCount))
        }

    override suspend fun setUpgrade(target: IStorage.StoredUpgrade) = withContext(Dispatchers.IO) {
        val upgrade = upgradesRepository.getUpgrade(target.id)
        upgradesRepository.updateUpgrade(
            upgrade.copy(
                level = target.level,
                power = target.power,
                cost = target.cost
            )
        )
    }

    override suspend fun getUpgrade(id: Int): IStorage.StoredUpgrade {
        return withContext(Dispatchers.IO) {
            upgradesRepository.getUpgrade(id).toStoredUpgrade(context)
        }
    }

    override suspend fun getUpgrades(): List<IStorage.StoredUpgrade> = withContext(Dispatchers.IO) {
        upgradesRepository.getUpgrades().map {
            it.toStoredUpgrade(context)
        }
    }

    @SuppressLint("DiscouragedApi")
    private fun Upgrade.toStoredUpgrade(context: Context) = IStorage.StoredUpgrade(
        id = this.id,
        title = context.getString(
            context.resources.getIdentifier(
                this.title,
                "string",
                context.packageName
            )
        ),
        description = context.getString(
            context.resources.getIdentifier(
                this.description,
                "string",
                context.packageName
            )
        ),
        level = this.level,
        power = this.power,
        cost = this.cost
    )

    override suspend fun reset() = withContext(Dispatchers.IO) {
        SaveDatabase.deleteDatabase(context)
    }
}