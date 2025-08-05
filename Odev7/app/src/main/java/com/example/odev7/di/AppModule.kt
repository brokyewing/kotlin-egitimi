package com.example.odev7.di

import android.content.Context
import androidx.room.Room
import com.example.odev7.data.dao.YapilacakDao
import com.example.odev7.data.db.Veritabani
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideVeritabani(@ApplicationContext context: Context): Veritabani {
        return Room.databaseBuilder(
            context,
            Veritabani::class.java,
            "yapilacak.sqlite"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideYapilacakDao(vt: Veritabani): YapilacakDao {
        return vt.yapilacakDao()
    }
} 