package org.shojhiseb.shared.feature_transaction.domain.validation

import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.models.TransactionType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TransactionValidatorTest {

    private val validator = TransactionValidator()

    @Test
    fun `valid transaction returns success`() {
        val transaction = Transaction(
            id = "1",
            type = TransactionType.EXPENSE,
            amount = 100.0,
            categoryId = "cat_1",
            note = null,
            date = 123456789L,
            createdAt = 123456789L,
            updatedAt = 123456789L,
            deletedAt = null
        )

        val result = validator.validate(transaction)
        assertTrue(result is ValidationResult.Success)
    }

    @Test
    fun `zero or negative amount returns error`() {
        val transaction = Transaction(
            id = "1",
            type = TransactionType.EXPENSE,
            amount = -10.0,
            categoryId = "cat_1",
            note = null,
            date = 123456789L,
            createdAt = 123456789L,
            updatedAt = 123456789L,
            deletedAt = null
        )

        val result = validator.validate(transaction)
        assertTrue(result is ValidationResult.Error)
        assertEquals("Amount must be greater than zero", (result as ValidationResult.Error).message)
    }

    @Test
    fun `blank category returns error`() {
        val transaction = Transaction(
            id = "1",
            type = TransactionType.EXPENSE,
            amount = 100.0,
            categoryId = "   ",
            note = null,
            date = 123456789L,
            createdAt = 123456789L,
            updatedAt = 123456789L,
            deletedAt = null
        )

        val result = validator.validate(transaction)
        assertTrue(result is ValidationResult.Error)
        assertEquals("Category must be selected", (result as ValidationResult.Error).message)
    }
}
