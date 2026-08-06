package org.shojhiseb.shared.feature_transaction.domain.validation

import org.shojhiseb.shared.feature_transaction.domain.models.Transaction

class TransactionValidator {
    fun validate(transaction: Transaction): ValidationResult {
        if (transaction.amount <= 0) {
            return ValidationResult.Error("Amount must be greater than zero")
        }
        if (transaction.categoryId.isBlank()) {
            return ValidationResult.Error("Category must be selected")
        }
        if (transaction.date <= 0) {
            return ValidationResult.Error("Invalid date")
        }
        return ValidationResult.Success
    }
}

sealed class ValidationResult {
    data object Success : ValidationResult()
    data class Error(val message: String) : ValidationResult()
}
