package org.shojhiseb.shared.core.analytics

actual class CrashlyticsTracker {
    actual fun logException(exception: Throwable) {
        // No-op for JVM
        println("CrashlyticsTracker [JVM]: Exception logged - ${exception.message}")
    }

    actual fun logMessage(message: String) {
        // No-op for JVM
        println("CrashlyticsTracker [JVM]: Message logged - $message")
    }
}
