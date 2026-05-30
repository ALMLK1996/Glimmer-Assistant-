package dev.glimmer.ai.core.di

import android.app.WindowManager
import android.content.Context
import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.glimmer.ai.memory.database.GlimmerDatabase
import dev.glimmer.ai.memory.database.MemoryDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWindowManager(@ApplicationContext context: Context): WindowManager {
        return context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    }

    @Provides
    @Singleton
    fun provideEncryptedSharedPreferences(
        @ApplicationContext context: Context
    ): androidx.security.crypto.EncryptedSharedPreferences {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "glimmer_encrypted_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        ) as androidx.security.crypto.EncryptedSharedPreferences
    }

    @Provides
    @Singleton
    fun provideGlimmerDatabase(
        @ApplicationContext context: Context
    ): GlimmerDatabase {
        return Room.databaseBuilder(
            context,
            GlimmerDatabase::class.java,
            "glimmer_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMemoryDao(database: GlimmerDatabase): MemoryDao {
        return database.memoryDao()
    }
}
