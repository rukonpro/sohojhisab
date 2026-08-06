package org.shojhiseb.shared.feature_ledger.data.mapper

import org.shojhiseb.shared.feature_ledger.domain.models.Ledger
import org.shojhiseb.shared.feature_ledger.domain.models.LedgerStatus

fun /* LedgerEntity */ Any.toDomain(): Ledger {
    // Dummy mapper due to SQLDelight AGP 9.0 compilation issue
    return Ledger(
        id = "",
        personName = "",
        amount = 0.0,
        status = LedgerStatus.PENDING,
        dueDate = null,
        createdAt = 0L,
        updatedAt = 0L,
        deletedAt = null
    )
}
