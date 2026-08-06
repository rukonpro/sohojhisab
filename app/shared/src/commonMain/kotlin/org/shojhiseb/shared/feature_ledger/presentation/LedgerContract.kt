package org.shojhiseb.shared.feature_ledger.presentation

import org.shojhiseb.shared.core.mvi.UiEffect
import org.shojhiseb.shared.core.mvi.UiEvent
import org.shojhiseb.shared.core.mvi.UiState
import org.shojhiseb.shared.feature_ledger.domain.models.Ledger

data class LedgerState(
    val toPayLedgers: List<Ledger> = emptyList(),
    val toReceiveLedgers: List<Ledger> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedTab: Int = 0 // 0 for To Pay, 1 for To Receive
) : UiState

sealed interface LedgerIntent : UiEvent {
    data object LoadLedgers : LedgerIntent
    data class OnTabSelected(val index: Int) : LedgerIntent
}

sealed interface LedgerEffect : UiEffect {
    // Add effects like showing snackbar
}
