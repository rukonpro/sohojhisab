package org.shojhiseb.shared.feature_transaction.data.mapper

import org.shojhiseb.shared.feature_transaction.domain.models.Category
import org.shojhiseb.shared.feature_transaction.domain.models.TransactionType
// import org.shojhiseb.shared.database.CategoryEntity

fun /* CategoryEntity */ Any.toCategoryDomain(): Category {
    // Dummy mapper since SQLDelight generation fails with AGP 9.0
    return Category(
        id = "",
        nameResId = "",
        type = TransactionType.INCOME,
        icon = "",
        color = "",
        priority = 0,
        isDefault = false,
        createdAt = 0L,
        updatedAt = 0L,
        deletedAt = null
    )
}
