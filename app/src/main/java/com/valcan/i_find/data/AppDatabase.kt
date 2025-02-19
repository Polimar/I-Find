package com.valcan.i_find.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.valcan.i_find.data.dao.ArmadioDao
import com.valcan.i_find.data.dao.VestitoDao
import com.valcan.i_find.data.model.Armadio
import com.valcan.i_find.data.model.Vestito

@Database(
    entities = [Armadio::class, Vestito::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun armadioDao(): ArmadioDao
    abstract fun vestitoDao(): VestitoDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 