package dev.stefano.stora.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.model.ProductDao

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}