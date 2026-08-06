package org.shojhiseb.shared.core.analytics

actual class CrashlyticsTracker {
    actual fun logException(exception: Throwable) {
        // No-op for JS
        console.log("CrashlyticsTracker [JS]: Exception logged - ${exception.message}")
    }

    actual fun logMessage(message: String) {
        // No-op for JS
        console.log("CrashlyticsTracker [JS]: Message logged - $message")
    }
}
