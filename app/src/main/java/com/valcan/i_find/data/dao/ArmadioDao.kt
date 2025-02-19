package com.valcan.i_find.data.dao

import androidx.room.*
import com.valcan.i_find.data.model.Armadio
import kotlinx.coroutines.flow.Flow

@Dao
interface ArmadioDao {
    @Query("SELECT * FROM armadi")
    fun getAll(): Flow<List<Armadio>>
    
    @Query("SELECT * FROM armadi WHERE id = :id")
    suspend fun getById(id: Long): Armadio?
    
    @Insert
    suspend fun insert(armadio: Armadio): Long
    
    @Update
    suspend fun update(armadio: Armadio)
    
    @Delete
    suspend fun delete(armadio: Armadio)
} 