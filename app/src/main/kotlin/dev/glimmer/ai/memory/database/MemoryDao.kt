package dev.glimmer.ai.memory.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.glimmer.ai.memory.models.Memory
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMemory(memory: Memory)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMemories(memories: List<Memory>)

    @Query("SELECT * FROM memory WHERE id = :id")
    suspend fun getMemoryById(id: String): Memory?

    @Query("SELECT * FROM memory ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getRecentMemories(limit: Int = 50): List<Memory>

    @Query("SELECT * FROM memory WHERE emotionalContext = :emotion ORDER BY timestamp DESC LIMIT :limit")
    suspend fun getMemoriesByEmotion(emotion: String, limit: Int = 20): List<Memory>

    @Query("SELECT * FROM memory ORDER BY timestamp DESC")
    fun getAllMemoriesFlow(): Flow<List<Memory>>

    @Query("SELECT * FROM memory WHERE timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp DESC")
    suspend fun getMemoriesByTimeRange(startTime: Long, endTime: Long): List<Memory>

    @Query("DELETE FROM memory WHERE timestamp < :cutoffTime")
    suspend fun deleteOldMemories(cutoffTime: Long)

    @Query("SELECT COUNT(*) FROM memory")
    suspend fun getMemoryCount(): Int

    @Query("SELECT AVG(relevanceScore) FROM memory")
    suspend fun getAverageRelevanceScore(): Float
}
