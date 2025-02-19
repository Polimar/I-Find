package com.valcan.i_find.data.dao

import androidx.room.*
import com.valcan.i_find.data.model.Vestito
import kotlinx.coroutines.flow.Flow

@Dao
interface VestitoDao {
    @Query("SELECT * FROM vestiti")
    fun getAll(): Flow<List<Vestito>>
    
    @Query("SELECT * FROM vestiti WHERE armadioId = :armadioId")
    fun getByArmadio(armadioId: Long): Flow<List<Vestito>>
    
    @Query("SELECT * FROM vestiti WHERE id = :id")
    suspend fun getById(id: Long): Vestito?
    
    @Insert
    suspend fun insert(vestito: Vestito): Long
    
    @Update
    suspend fun update(vestito: Vestito)
    
    @Delete
    suspend fun delete(vestito: Vestito)
} 