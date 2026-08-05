package org.shojhiseb.shared.core.error

import io.github.aakira.napier.Napier
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * A centralized CoroutineExceptionHandler to catch and log uncaught exceptions 
 * gracefully, preventing silent app crashes.
 */
val GlobalCoroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    Napier.e(
        message = "Uncaught coroutine exception: ${exception.message}",
        throwable = exception,
        tag = "GlobalErrorHandler"
    )
    // Additional logic like reporting to Crashlytics can be triggered from here.
}
