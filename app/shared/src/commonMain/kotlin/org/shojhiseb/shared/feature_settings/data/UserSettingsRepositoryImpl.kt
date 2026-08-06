package org.shojhiseb.shared.feature_settings.data

import com.russhwolf.settings.Settings

interface UserSettingsRepository {
    fun getTheme(): String
    fun setTheme(theme: String)
    fun getLanguage(): String
    fun setLanguage(language: String)
    fun getCurrency(): String
    fun setCurrency(currency: String)
}

class UserSettingsRepositoryImpl(
    private val settings: Settings
) : UserSettingsRepository {

    override fun getTheme(): String {
        return settings.getString("theme", "System Default")
    }

    override fun setTheme(theme: String) {
        settings.putString("theme", theme)
    }

    override fun getLanguage(): String {
        return settings.getString("language", "English")
    }

    override fun setLanguage(language: String) {
        settings.putString("language", language)
    }

    override fun getCurrency(): String {
        return settings.getString("currency", "BDT")
    }

    override fun setCurrency(currency: String) {
        settings.putString("currency", currency)
    }
}
