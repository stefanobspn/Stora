package dev.stefano.stora.data.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "pegawai")
data class Pegawai(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val position: String,
    val phoneNumber: String
)

@Dao
interface PegawaiDao {
    @Query("SELECT * FROM pegawai ORDER BY name ASC")
    fun getAllPegawai(): Flow<List<Pegawai>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPegawai(pegawai: Pegawai)

    @Update
    suspend fun updatePegawai(pegawai: Pegawai)

    @Delete
    suspend fun deletePegawai(pegawai: Pegawai)
}
