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
    val errorMessage: String? = null,
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val balance: Double = 0.0
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
                            totalIncome = resource.data.filter { it.type == org.shojhiseb.shared.feature_transaction.domain.models.TransactionType.INCOME }.sumOf { it.amount },
                            totalExpense = resource.data.filter { it.type == org.shojhiseb.shared.feature_transaction.domain.models.TransactionType.EXPENSE }.sumOf { it.amount },
                            balance = resource.data.sumOf { if(it.type == org.shojhiseb.shared.feature_transaction.domain.models.TransactionType.INCOME) it.amount else -it.amount },
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
                id = "0",
                amount = amount,
                note = note,
                type = type,
                categoryId = "1", // Default category for quick templates
                date = 0L,
                createdAt = 0L,
                updatedAt = 0L
            )
            transactionUseCases.insertTransaction(transaction)
        }
    }
}
