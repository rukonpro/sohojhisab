package org.shojhiseb.shared.feature_ledger.domain.models

data class Ledger(
    val id: String,
    val personName: String,
    val amount: Double,
    val status: LedgerStatus,
    val dueDate: Long?,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Long? = null
)
