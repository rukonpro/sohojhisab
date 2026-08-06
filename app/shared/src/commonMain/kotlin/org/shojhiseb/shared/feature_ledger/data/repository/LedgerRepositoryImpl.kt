package org.shojhiseb.shared.feature_ledger.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.database.ShojhisebDatabase
import org.shojhiseb.shared.feature_ledger.data.mapper.toDomain
import org.shojhiseb.shared.feature_ledger.domain.models.Ledger
import org.shojhiseb.shared.feature_ledger.domain.repository.LedgerRepository

class LedgerRepositoryImpl(
    private val database: ShojhisebDatabase
) : LedgerRepository {
    private val queries = database.ledgerQueries

    override fun getAllLedgers(): Flow<Resource<List<Ledger>>> {
        return queries.selectAll()
            .asFlow()
            .mapToList(Dispatchers.Default)
            .map { entities ->
                Resource.Success(entities.map { it.toDomain() }) as Resource<List<Ledger>>
            }
            .catch { e ->
                emit(Resource.Error("Failed to fetch ledgers", e))
            }
    }

    override suspend fun insertLedger(ledger: Ledger): Resource<Unit> {
        return try {
            queries.insertLedger(
                id = ledger.id,
                personName = ledger.personName,
                amount = ledger.amount,
                status = ledger.status.name,
                dueDate = ledger.dueDate,
                created_at = ledger.createdAt,
                updated_at = ledger.updatedAt,
                deleted_at = ledger.deletedAt
            )
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to insert ledger", e)
        }
    }

    override suspend fun softDeleteLedger(id: String): Resource<Unit> {
        return try {
            val timestamp = 0L 
            queries.softDeleteLedger(deleted_at = timestamp, id = id)
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error("Failed to delete ledger", e)
        }
    }
}
