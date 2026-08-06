package org.shojhiseb.shared.feature_analytics.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_transaction.domain.models.TransactionType
import org.shojhiseb.shared.feature_transaction.domain.usecase.TransactionUseCases

class AnalyticsScreenModel(
    private val transactionUseCases: TransactionUseCases
) : StateScreenModel<AnalyticsState>(AnalyticsState()) {

    init {
        loadData()
    }

    private fun loadData() {
        screenModelScope.launch {
            transactionUseCases.getTransactions().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val transactions = resource.data
                        var totalIncome = 0.0
                        var totalExpense = 0.0
                        
                        // Example breakdown logic
                        val categoryMap = mutableMapOf<String, Float>()

                        transactions.forEach { txn ->
                            if (txn.type == TransactionType.INCOME) {
                                totalIncome += txn.amount
                            } else {
                                totalExpense += txn.amount
                                val catValue = categoryMap[txn.categoryId] ?: 0f
                                categoryMap[txn.categoryId] = catValue + txn.amount.toFloat()
                            }
                        }

                        val mockCashFlow = listOf(
                            ChartData("Income", totalIncome.toFloat(), 0xFF4CAF50),
                            ChartData("Expense", totalExpense.toFloat(), 0xFFF44336)
                        )

                        val categoryBreakdown = categoryMap.map { (catId, amount) ->
                            ChartData(catId, amount, 0xFF2196F3) // Dummy color
                        }

                        mutableState.value = state.value.copy(
                            totalIncome = totalIncome,
                            totalExpense = totalExpense,
                            cashFlowData = mockCashFlow,
                            categoryBreakdown = categoryBreakdown,
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
}
