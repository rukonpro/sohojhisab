package org.shojhiseb.shared.feature_ledger.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_ledger.domain.models.Ledger
import org.shojhiseb.shared.feature_ledger.domain.repository.LedgerRepository

class GetLedgersUseCase(
    private val repository: LedgerRepository
) {
    operator fun invoke(): Flow<Resource<List<Ledger>>> {
        return repository.getAllLedgers()
    }
}

class InsertLedgerUseCase(
    private val repository: LedgerRepository
) {
    suspend operator fun invoke(ledger: Ledger): Resource<Unit> {
        if (ledger.amount <= 0) {
            return Resource.Error("Amount must be greater than zero")
        }
        if (ledger.personName.isBlank()) {
            return Resource.Error("Person name cannot be empty")
        }
        return repository.insertLedger(ledger)
    }
}

class DeleteLedgerUseCase(
    private val repository: LedgerRepository
) {
    suspend operator fun invoke(id: String): Resource<Unit> {
        return repository.softDeleteLedger(id)
    }
}

data class LedgerUseCases(
    val getLedgers: GetLedgersUseCase,
    val insertLedger: InsertLedgerUseCase,
    val deleteLedger: DeleteLedgerUseCase
)
