package dev.stefano.stora.data.repository

import dev.stefano.stora.data.model.Cabang
import dev.stefano.stora.data.model.CabangDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface CabangRepository {
    fun getAllCabang(): Flow<List<Cabang>>
    suspend fun insertCabang(cabang: Cabang)
    suspend fun updateCabang(cabang: Cabang)
    suspend fun deleteCabang(cabang: Cabang)
}

class CabangRepositoryImpl @Inject constructor(
    private val cabangDao: CabangDao
) : CabangRepository {
    override fun getAllCabang(): Flow<List<Cabang>> = cabangDao.getAllCabang()
    override suspend fun insertCabang(cabang: Cabang) = cabangDao.insertCabang(cabang)
    override suspend fun updateCabang(cabang: Cabang) = cabangDao.updateCabang(cabang)
    override suspend fun deleteCabang(cabang: Cabang) = cabangDao.deleteCabang(cabang)
}
