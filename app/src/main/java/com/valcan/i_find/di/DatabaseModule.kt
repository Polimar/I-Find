package com.valcan.i_find.di

import android.content.Context
import com.valcan.i_find.data.AppDatabase
import com.valcan.i_find.data.dao.ArmadioDao
import com.valcan.i_find.data.dao.VestitoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }
    
    @Provides
    fun provideArmadioDao(database: AppDatabase): ArmadioDao {
        return database.armadioDao()
    }
    
    @Provides
    fun provideVestitoDao(database: AppDatabase): VestitoDao {
        return database.vestitoDao()
    }
} 