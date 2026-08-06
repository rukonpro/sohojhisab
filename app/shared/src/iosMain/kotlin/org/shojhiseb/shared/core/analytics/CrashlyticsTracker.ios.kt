package org.shojhiseb.shared.core.analytics

import io.github.aakira.napier.Napier

actual class CrashlyticsTracker {
    // In a real app, this would use FIRCrashlytics.crashlytics() via Cocoapods interop
    actual fun logException(exception: Throwable) {
        Napier.e("Crashlytics (iOS): Exception logged", exception)
    }

    actual fun logMessage(message: String) {
        Napier.d("Crashlytics (iOS): $message")
    }
}
