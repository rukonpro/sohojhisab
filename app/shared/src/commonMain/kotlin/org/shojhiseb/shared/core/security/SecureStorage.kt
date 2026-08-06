package org.shojhiseb.shared.core.security

expect class SecureStorage {
    fun savePin(pin: String)
    fun getPin(): String?
    fun clearPin()
}
