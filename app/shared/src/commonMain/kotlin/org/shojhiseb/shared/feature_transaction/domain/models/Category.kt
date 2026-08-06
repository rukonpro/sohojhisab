package org.shojhiseb.shared.feature_transaction.domain.models

data class Category(
    val id: String,
    val nameResId: String,
    val type: TransactionType,
    val icon: String,
    val color: String,
    val priority: Int,
    val isDefault: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)
