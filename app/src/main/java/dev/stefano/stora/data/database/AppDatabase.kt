package dev.stefano.stora.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.model.ProductDao
import dev.stefano.stora.data.model.TransactionEntity
import dev.stefano.stora.data.model.TransactionItemEntity
import dev.stefano.stora.data.model.TransactionDao

@Database(
    entities = [Product::class, TransactionEntity::class, TransactionItemEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun transactionDao(): TransactionDao
}