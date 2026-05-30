package dev.glimmer.ai.memory.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import dev.glimmer.ai.memory.database.converters.FloatArrayConverter
import dev.glimmer.ai.memory.models.Memory

@Database(
    entities = [Memory::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(
    FloatArrayConverter::class
)
abstract class GlimmerDatabase : RoomDatabase() {
    abstract fun memoryDao(): MemoryDao
}
