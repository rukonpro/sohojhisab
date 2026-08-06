package org.shojhiseb.shared.feature_settings.presentation

import org.shojhiseb.shared.core.mvi.UiEffect
import org.shojhiseb.shared.core.mvi.UiEvent
import org.shojhiseb.shared.core.mvi.UiState

data class SettingsState(
    val theme: String = "System Default",
    val language: String = "English",
    val currency: String = "BDT"
) : UiState

sealed interface SettingsIntent : UiEvent {
    data class OnThemeChanged(val theme: String) : SettingsIntent
    data class OnLanguageChanged(val language: String) : SettingsIntent
    data class OnCurrencyChanged(val currency: String) : SettingsIntent
    data class ExportData(val filePath: String) : SettingsIntent
}

sealed interface SettingsEffect : UiEffect {
    // Add effects later if needed
}
