package org.shojhiseb.shared.feature_settings.presentation

import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.launch
import org.shojhiseb.shared.core.export.ExportManager
import org.shojhiseb.shared.feature_settings.data.UserSettingsRepository

class SettingsScreenModel(
    private val userSettingsRepository: UserSettingsRepository,
    private val exportManager: ExportManager
) : StateScreenModel<SettingsState>(
    SettingsState(
        theme = userSettingsRepository.getTheme(),
        language = userSettingsRepository.getLanguage(),
        currency = userSettingsRepository.getCurrency()
    )
) {
    fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.OnThemeChanged -> {
                userSettingsRepository.setTheme(intent.theme)
                mutableState.value = state.value.copy(theme = intent.theme)
            }
            is SettingsIntent.OnLanguageChanged -> {
                userSettingsRepository.setLanguage(intent.language)
                mutableState.value = state.value.copy(language = intent.language)
            }
            is SettingsIntent.OnCurrencyChanged -> {
                userSettingsRepository.setCurrency(intent.currency)
                mutableState.value = state.value.copy(currency = intent.currency)
            }
            is SettingsIntent.ExportData -> {
                screenModelScope.launch {
                    val success = exportManager.exportToCsv(intent.filePath)
                    // Optionally dispatch effect based on success
                }
            }
        }
    }
}
