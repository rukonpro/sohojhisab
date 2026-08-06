package org.shojhiseb.shared.feature_transaction.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.database.ShojhisebDatabase
import org.shojhiseb.shared.feature_transaction.data.mapper.toTransactionDomain
import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val database: ShojhisebDatabase
) : TransactionRepository {
    private val queries = database.transactionQueries

    override fun getAllTransactions(): Flow<Resource<List<Transaction>>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default) // Using Default instead of IO in KMP common
            .map { entities ->
                Resource.Success(entities.map { it.toTransactionDomain() }) as Resource<List<Transaction>>
            }
            .catch { e ->
                emit(Resource.Error("Failed to fetch transactions", e))
            }
    }

    override suspend fun insertTransaction(transaction: Transaction): Resource<Unit> {
        return try {
            queries.insertTransaction(
                id = transaction.id,
                type = transaction.type.name,
                amount = transaction.amount,
                categoryId = transaction.categoryId,
                note = transaction.note,
                date = transaction.date,
                created_at = transaction.createdAt,
                updated_at = transaction.updatedAt,
                deleted_at = transaction.deletedAt
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to insert transaction", e)
        }
    }

    override suspend fun softDeleteTransaction(id: String): Resource<Unit> {
        return try {
            // Assuming current timestamp for soft delete
            // You can pass timestamp from domain layer or generate here
            val timestamp = 0L 
            queries.softDeleteTransaction(deleted_at = timestamp, id = id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to delete transaction", e)
        }
    }
}
