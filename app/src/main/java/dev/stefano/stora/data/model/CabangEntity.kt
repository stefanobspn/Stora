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

@Entity(tableName = "cabang")
data class Cabang(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val address: String,
    val phoneNumber: String
)

@Dao
interface CabangDao {
    @Query("SELECT * FROM cabang ORDER BY name ASC")
    fun getAllCabang(): Flow<List<Cabang>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCabang(cabang: Cabang)

    @Update
    suspend fun updateCabang(cabang: Cabang)

    @Delete
    suspend fun deleteCabang(cabang: Cabang)
}
