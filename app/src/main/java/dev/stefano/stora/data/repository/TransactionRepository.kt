package dev.stefano.stora.data.repository

import dev.stefano.stora.data.model.TransactionDao
import dev.stefano.stora.data.model.TransactionEntity
import dev.stefano.stora.data.model.TransactionItemEntity
import dev.stefano.stora.data.model.TransactionWithItems
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface TransactionRepository {
    fun getAllTransactions(): Flow<List<TransactionWithItems>>
    suspend fun saveTransaction(transaction: TransactionEntity, items: List<TransactionItemEntity>)
}

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override fun getAllTransactions(): Flow<List<TransactionWithItems>> = transactionDao.getAllTransactions()

    override suspend fun saveTransaction(transaction: TransactionEntity, items: List<TransactionItemEntity>) {
        val id = transactionDao.insertTransaction(transaction)
        val itemsWithId = items.map { it.copy(transactionId = id.toInt()) }
        transactionDao.insertTransactionItems(itemsWithId)
    }
}
