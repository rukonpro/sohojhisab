package org.shojhiseb.shared.feature_ledger.domain.repository

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_ledger.domain.models.Ledger

interface LedgerRepository {
    fun getAllLedgers(): Flow<Resource<List<Ledger>>>
    suspend fun insertLedger(ledger: Ledger): Resource<Unit>
    suspend fun softDeleteLedger(id: String): Resource<Unit>
}
