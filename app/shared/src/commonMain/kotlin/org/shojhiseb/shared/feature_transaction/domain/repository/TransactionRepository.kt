package org.shojhiseb.shared.feature_transaction.domain.repository

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Transaction

interface TransactionRepository {
    fun getAllTransactions(): Flow<Resource<List<Transaction>>>
    suspend fun insertTransaction(transaction: Transaction): Resource<Unit>
    suspend fun softDeleteTransaction(id: String): Resource<Unit>
}
