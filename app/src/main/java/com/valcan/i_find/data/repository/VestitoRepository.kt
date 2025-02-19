package com.valcan.i_find.data.repository

import com.valcan.i_find.data.dao.VestitoDao
import com.valcan.i_find.data.model.Vestito
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VestitoRepository @Inject constructor(
    private val vestitoDao: VestitoDao
) {
    fun getAllVestiti(): Flow<List<Vestito>> = vestitoDao.getAll()
    
    fun getVestitiByArmadio(armadioId: Long): Flow<List<Vestito>> = 
        vestitoDao.getByArmadio(armadioId)
    
    suspend fun getVestitoById(id: Long): Vestito? = vestitoDao.getById(id)
    
    suspend fun insertVestito(vestito: Vestito): Long = vestitoDao.insert(vestito)
    
    suspend fun updateVestito(vestito: Vestito) = vestitoDao.update(vestito)
    
    suspend fun deleteVestito(vestito: Vestito) = vestitoDao.delete(vestito)
} 