package org.shojhiseb.shared.feature_transaction.presentation

import org.shojhiseb.shared.core.mvi.UiEffect
import org.shojhiseb.shared.core.mvi.UiEvent
import org.shojhiseb.shared.core.mvi.UiState
import org.shojhiseb.shared.feature_transaction.domain.models.Category
import org.shojhiseb.shared.feature_transaction.domain.models.TransactionType

data class TransactionState(
    val amount: String = "",
    val note: String = "",
    val selectedType: TransactionType = TransactionType.EXPENSE,
    val selectedCategory: Category? = null,
    val categories: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : UiState

sealed interface TransactionIntent : UiEvent {
    data class OnAmountChange(val amount: String) : TransactionIntent
    data class OnNoteChange(val note: String) : TransactionIntent
    data class OnTypeChange(val type: TransactionType) : TransactionIntent
    data class OnCategorySelect(val category: Category) : TransactionIntent
    data object OnSubmit : TransactionIntent
    data object LoadCategories : TransactionIntent
}

sealed interface TransactionEffect : UiEffect {
    data object NavigateBack : TransactionEffect
    data class ShowToast(val message: String) : TransactionEffect
}
