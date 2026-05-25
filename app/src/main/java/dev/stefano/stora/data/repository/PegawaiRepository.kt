package dev.stefano.stora.data.repository

import dev.stefano.stora.data.model.Pegawai
import dev.stefano.stora.data.model.PegawaiDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface PegawaiRepository {
    fun getAllPegawai(): Flow<List<Pegawai>>
    suspend fun insertPegawai(pegawai: Pegawai)
    suspend fun updatePegawai(pegawai: Pegawai)
    suspend fun deletePegawai(pegawai: Pegawai)
}

class PegawaiRepositoryImpl @Inject constructor(
    private val pegawaiDao: PegawaiDao
) : PegawaiRepository {
    override fun getAllPegawai(): Flow<List<Pegawai>> = pegawaiDao.getAllPegawai()
    override suspend fun insertPegawai(pegawai: Pegawai) = pegawaiDao.insertPegawai(pegawai)
    override suspend fun updatePegawai(pegawai: Pegawai) = pegawaiDao.updatePegawai(pegawai)
    override suspend fun deletePegawai(pegawai: Pegawai) = pegawaiDao.deletePegawai(pegawai)
}
