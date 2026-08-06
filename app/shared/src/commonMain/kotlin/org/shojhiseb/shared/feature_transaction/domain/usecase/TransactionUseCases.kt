package org.shojhiseb.shared.feature_transaction.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.repository.TransactionRepository
import org.shojhiseb.shared.feature_transaction.domain.validation.TransactionValidator
import org.shojhiseb.shared.feature_transaction.domain.validation.ValidationResult

class GetTransactionsUseCase(
    private val repository: TransactionRepository
) {
    operator fun invoke(): Flow<Resource<List<Transaction>>> {
        return repository.getAllTransactions()
    }
}

class InsertTransactionUseCase(
    private val repository: TransactionRepository,
    private val validator: TransactionValidator
) {
    suspend operator fun invoke(transaction: Transaction): Resource<Unit> {
        return when (val result = validator.validate(transaction)) {
            is ValidationResult.Error -> Resource.Error(result.message)
            ValidationResult.Success -> repository.insertTransaction(transaction)
        }
    }
}

class DeleteTransactionUseCase(
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(id: String): Resource<Unit> {
        return repository.softDeleteTransaction(id)
    }
}

data class TransactionUseCases(
    val getTransactions: GetTransactionsUseCase,
    val insertTransaction: InsertTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase
)
