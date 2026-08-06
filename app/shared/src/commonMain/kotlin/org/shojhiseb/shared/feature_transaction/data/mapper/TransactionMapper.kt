package org.shojhiseb.shared.feature_transaction.data.mapper

import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.models.TransactionType

fun /* TransactionEntity */ Any.toDomain(): Transaction {
    // Dummy mapper due to SQLDelight AGP 9.0 compilation issue
    return Transaction(
        id = "",
        type = TransactionType.EXPENSE,
        amount = 0.0,
        categoryId = "",
        note = null,
        date = 0L,
        createdAt = 0L,
        updatedAt = 0L,
        deletedAt = null
    )
}
