package org.shojhiseb.shared.feature_ledger.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.shojhiseb.shared.core.resource.Resource
import org.shojhiseb.shared.feature_ledger.domain.models.LedgerType
import org.shojhiseb.shared.feature_ledger.domain.usecase.LedgerUseCases

class LedgerScreenModel(
    private val ledgerUseCases: LedgerUseCases
) : StateScreenModel<LedgerState>(LedgerState()) {

    init {
        handleIntent(LedgerIntent.LoadLedgers)
    }

    fun handleIntent(intent: LedgerIntent) {
        when (intent) {
            is LedgerIntent.LoadLedgers -> loadLedgers()
            is LedgerIntent.OnTabSelected -> {
                mutableState.value = state.value.copy(selectedTab = intent.index)
            }
        }
    }

    private fun loadLedgers() {
        screenModelScope.launch {
            ledgerUseCases.getLedgers().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val allLedgers = resource.data
                        val toPay = allLedgers.filter { it.type == LedgerType.TO_PAY }
                        val toReceive = allLedgers.filter { it.type == LedgerType.TO_RECEIVE }
                        
                        mutableState.value = state.value.copy(
                            toPayLedgers = toPay,
                            toReceiveLedgers = toReceive,
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
}
