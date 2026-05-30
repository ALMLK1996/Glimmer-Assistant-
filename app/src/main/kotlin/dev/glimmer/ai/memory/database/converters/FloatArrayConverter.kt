package dev.glimmer.ai.memory.database.converters

import androidx.room.TypeConverter
import java.nio.ByteBuffer

class FloatArrayConverter {
    @TypeConverter
    fun fromFloatArray(value: FloatArray?): ByteArray? {
        if (value == null) return null
        val buffer = ByteBuffer.allocate(value.size * Float.SIZE_BYTES)
        for (float in value) {
            buffer.putFloat(float)
        }
        return buffer.array()
    }

    @TypeConverter
    fun toFloatArray(value: ByteArray?): FloatArray? {
        if (value == null) return null
        val buffer = ByteBuffer.wrap(value)
        val floatArray = FloatArray(value.size / Float.SIZE_BYTES)
        for (i in floatArray.indices) {
            floatArray[i] = buffer.float
        }
        return floatArray
    }
}
