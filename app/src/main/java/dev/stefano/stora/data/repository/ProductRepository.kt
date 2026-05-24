package dev.stefano.stora.data.repository

import dev.stefano.stora.data.model.Product
import dev.stefano.stora.data.model.ProductDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ProductRepository {
    fun getAllProducts(): Flow<List<Product>>
    suspend fun insertProduct(product: Product)
}

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao
): ProductRepository {
    override fun getAllProducts(): Flow<List<Product>> = productDao.getAllProducts()
    override suspend fun insertProduct(product: Product) = productDao.insertProduct(product)
}

