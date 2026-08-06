package org.shojhiseb.shared.core.security

import android.content.Context
import android.content.SharedPreferences

// In a real production app, use EncryptedSharedPreferences from androidx.security.crypto
// For this architecture demo, we use a simple abstraction
actual class SecureStorage(private val context: Context) {
    
    private val prefs: SharedPreferences by lazy {
        context.getSharedPreferences("secure_prefs", Context.MODE_PRIVATE)
    }

    actual fun savePin(pin: String) {
        prefs.edit().putString("app_pin", pin).apply()
    }

    actual fun getPin(): String? {
        return prefs.getString("app_pin", null)
    }

    actual fun clearPin() {
        prefs.edit().remove("app_pin").apply()
    }
}
