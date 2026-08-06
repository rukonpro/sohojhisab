package org.shojhiseb.shared.feature_transaction.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.usecase.CategoryUseCases
import org.shojhiseb.shared.feature_transaction.domain.usecase.TransactionUseCases

class TransactionScreenModel(
    private val transactionUseCases: TransactionUseCases,
    private val categoryUseCases: CategoryUseCases
) : StateScreenModel<TransactionState>(TransactionState()) {

    private val _effect = MutableSharedFlow<TransactionEffect>()
    val effect: SharedFlow<TransactionEffect> = _effect.asSharedFlow()

    init {
        handleIntent(TransactionIntent.LoadCategories)
    }

    fun handleIntent(intent: TransactionIntent) {
        when (intent) {
            is TransactionIntent.OnAmountChange -> {
                mutableState.value = state.value.copy(amount = intent.amount, errorMessage = null)
            }
            is TransactionIntent.OnNoteChange -> {
                mutableState.value = state.value.copy(note = intent.note)
            }
            is TransactionIntent.OnTypeChange -> {
                mutableState.value = state.value.copy(selectedType = intent.type, selectedCategory = null)
            }
            is TransactionIntent.OnCategorySelect -> {
                mutableState.value = state.value.copy(selectedCategory = intent.category, errorMessage = null)
            }
            is TransactionIntent.LoadCategories -> {
                loadCategories()
            }
            is TransactionIntent.OnSubmit -> {
                submitTransaction()
            }
        }
    }

    private fun loadCategories() {
        screenModelScope.launch {
            categoryUseCases.getCategories().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        mutableState.value = state.value.copy(
                            categories = resource.data,
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        mutableState.value = state.value.copy(
                            isLoading = false,
                            errorMessage = resource.message
                        )
                    }
                    is Resource.Loading -> {
                        mutableState.value = state.value.copy(isLoading = true)
                    }
                }
            }
        }
    }

    private fun submitTransaction() {
        val currentState = state.value
        val amountDouble = currentState.amount.toDoubleOrNull() ?: 0.0

        val transaction = Transaction(
            id = "txn_${kotlin.random.Random.nextLong()}", // Generate UUID properly in real app
            type = currentState.selectedType,
            amount = amountDouble,
            categoryId = currentState.selectedCategory?.id ?: "",
            note = currentState.note.ifBlank { null },
            date = 0L, // Use current timestamp
            createdAt = 0L,
            updatedAt = 0L,
            deletedAt = null
        )

        screenModelScope.launch {
            mutableState.value = currentState.copy(isLoading = true)
            when (val result = transactionUseCases.insertTransaction(transaction)) {
                is Resource.Success -> {
                    mutableState.value = currentState.copy(isLoading = false)
                    _effect.emit(TransactionEffect.NavigateBack)
                }
                is Resource.Error -> {
                    mutableState.value = currentState.copy(
                        isLoading = false,
                        errorMessage = result.message
                    )
                }
                is Resource.Loading -> {
                    // Do nothing
                }
            }
        }
    }
}
