package org.shojhiseb.shared.core.security

actual class SecureStorage {
    private var mockPin: String? = null

    actual fun savePin(pin: String) {
        mockPin = pin
    }

    actual fun getPin(): String? {
        return mockPin
    }

    actual fun clearPin() {
        mockPin = null
    }
}
