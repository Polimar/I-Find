package com.valcan.i_find.data.repository

import com.valcan.i_find.data.dao.ArmadioDao
import com.valcan.i_find.data.model.Armadio
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ArmadioRepository @Inject constructor(
    private val armadioDao: ArmadioDao
) {
    fun getAllArmadi(): Flow<List<Armadio>> = armadioDao.getAll()
    
    suspend fun getArmadioById(id: Long): Armadio? = armadioDao.getById(id)
    
    suspend fun insertArmadio(armadio: Armadio): Long = armadioDao.insert(armadio)
    
    suspend fun updateArmadio(armadio: Armadio) = armadioDao.update(armadio)
    
    suspend fun deleteArmadio(armadio: Armadio) = armadioDao.delete(armadio)
} 