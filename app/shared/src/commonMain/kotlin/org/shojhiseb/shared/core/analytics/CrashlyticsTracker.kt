package org.shojhiseb.shared.core.analytics

expect class CrashlyticsTracker {
    fun logException(exception: Throwable)
    fun logMessage(message: String)
}
