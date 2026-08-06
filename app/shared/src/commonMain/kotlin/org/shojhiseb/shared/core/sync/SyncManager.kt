package org.shojhiseb.shared.core.sync

import kotlinx.coroutines.flow.Flow
import org.shojhiseb.shared.core.resource.Resource

interface SyncManager {
    fun syncPendingTransactions(): Flow<Resource<Unit>>
    fun syncPendingCategories(): Flow<Resource<Unit>>
    fun syncPendingLedgers(): Flow<Resource<Unit>>
    fun fullSync(): Flow<Resource<Unit>>
}
