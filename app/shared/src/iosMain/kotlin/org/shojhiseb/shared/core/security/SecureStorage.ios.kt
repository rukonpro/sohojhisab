package org.shojhiseb.shared.core.security

import platform.Foundation.NSUserDefaults

actual class SecureStorage {
    
    // In a real production app, use iOS Keychain Services via C-Interop or a library like Multiplatform Settings with Keychain
    // For this architecture demo, we use a simple abstraction
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun savePin(pin: String) {
        defaults.setObject(pin, forKey = "app_pin")
    }

    actual fun getPin(): String? {
        return defaults.stringForKey("app_pin")
    }

    actual fun clearPin() {
        defaults.removeObjectForKey("app_pin")
    }
}
