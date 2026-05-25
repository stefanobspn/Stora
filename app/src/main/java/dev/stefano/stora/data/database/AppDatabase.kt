package dev.stefano.stora.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.model.ProductDao
import dev.stefano.stora.data.model.TransactionEntity
import dev.stefano.stora.data.model.TransactionItemEntity
import dev.stefano.stora.data.model.TransactionDao
import dev.stefano.stora.data.model.Pegawai
import dev.stefano.stora.data.model.PegawaiDao
import dev.stefano.stora.data.model.Member
import dev.stefano.stora.data.model.MemberDao
import dev.stefano.stora.data.model.Cabang
import dev.stefano.stora.data.model.CabangDao

@Database(
    entities = [Product::class, TransactionEntity::class, TransactionItemEntity::class, Pegawai::class, Member::class, Cabang::class],
    version = 6,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao
    abstract fun pegawaiDao(): PegawaiDao
    abstract fun memberDao(): MemberDao
    abstract fun cabangDao(): CabangDao
}
