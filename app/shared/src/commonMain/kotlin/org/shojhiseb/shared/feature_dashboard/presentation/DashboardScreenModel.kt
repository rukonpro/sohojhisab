package org.shojhiseb.shared.feature_dashboard.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.Transaction
import org.shojhiseb.shared.feature_transaction.domain.usecase.TransactionUseCases

data class DashboardState(
    val recentTransactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class DashboardScreenModel(
    private val transactionUseCases: TransactionUseCases
) : StateScreenModel<DashboardState>(DashboardState()) {

    init {
        loadRecentTransactions()
    }

    private fun loadRecentTransactions() {
        screenModelScope.launch {
            transactionUseCases.getTransactions().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        // In a real app, you might want to sort by date and take the top N
                        mutableState.value = state.value.copy(
                            recentTransactions = resource.data.take(10),
                            isLoading = false,
                            errorMessage = null
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

    fun addQuickTransaction(amount: Double, note: String, type: org.shojhiseb.shared.feature_transaction.domain.models.TransactionType) {
        screenModelScope.launch {
            val transaction = Transaction(
                id = 0,
                amount = amount,
                note = note,
                type = type,
                categoryId = 1, // Default category for quick templates
                date = kotlinx.datetime.Clock.System.now().toEpochMilliseconds()
            )
            transactionUseCases.insertTransaction(transaction)
        }
    }
}
