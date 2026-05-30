package dev.glimmer.ai.memory.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "memory")
data class Memory(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "content")
    val content: String,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "embedding", typeAffinity = ColumnInfo.BLOB)
    val embedding: FloatArray,  // Vector representation for semantic search

    @ColumnInfo(name = "emotional_context")
    val emotionalContext: String,  // HAPPY, FOCUSED, CARING, etc.

    @ColumnInfo(name = "relevance_score")
    val relevanceScore: Float,  // 0.0 - 1.0

    @ColumnInfo(name = "activity_context")
    val activityContext: String? = null,  // App/context when memory was created

    @ColumnInfo(name = "tags")
    val tags: String? = null  // Comma-separated tags
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Memory) return false
        if (id != other.id) return false
        if (content != other.content) return false
        if (timestamp != other.timestamp) return false
        if (!embedding.contentEquals(other.embedding)) return false
        if (emotionalContext != other.emotionalContext) return false
        if (relevanceScore != other.relevanceScore) return false
        if (activityContext != other.activityContext) return false
        if (tags != other.tags) return false
        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + timestamp.hashCode()
        result = 31 * result + embedding.contentHashCode()
        result = 31 * result + emotionalContext.hashCode()
        result = 31 * result + relevanceScore.hashCode()
        result = 31 * result + (activityContext?.hashCode() ?: 0)
        result = 31 * result + (tags?.hashCode() ?: 0)
        return result
    }
}
