package org.shojhiseb.shared.feature_transaction.domain.models

data class Transaction(
    val id: String,
    val type: TransactionType,
    val amount: Double,
    val categoryId: String,
    val note: String?,
    val date: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)
