package org.shojhiseb.shared.core.analytics

import io.github.aakira.napier.Napier

actual class CrashlyticsTracker {
    // In a real app, this would use FirebaseCrashlytics.getInstance()
    actual fun logException(exception: Throwable) {
        Napier.e("Crashlytics (Android): Exception logged", exception)
    }

    actual fun logMessage(message: String) {
        Napier.d("Crashlytics (Android): $message")
    }
}
