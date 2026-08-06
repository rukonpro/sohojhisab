package org.shojhiseb.shared.feature_analytics.presentation

import org.shojhiseb.shared.core.mvi.UiEffect
import org.shojhiseb.shared.core.mvi.UiEvent
import org.shojhiseb.shared.core.mvi.UiState

data class ChartData(
    val label: String,
    val value: Float,
    val colorHex: Long
)

data class AnalyticsState(
    val totalIncome: Double = 0.0,
    val totalExpense: Double = 0.0,
    val cashFlowData: List<ChartData> = emptyList(),
    val categoryBreakdown: List<ChartData> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
) : UiState

sealed interface AnalyticsIntent : UiEvent {
    data object LoadData : AnalyticsIntent
}

sealed interface AnalyticsEffect : UiEffect {
    // No specific effects needed yet
}
