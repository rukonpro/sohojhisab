package org.shojhiseb.shared.core.mvi

/**
 * Represents the current state of the UI.
 */
interface UiState

/**
 * Represents a user action or intent that the ViewModel should handle.
 */
interface UiEvent

/**
 * Represents a one-off side effect (like navigation, showing a snackbar) 
 * that shouldn't persist across configuration changes.
 */
interface UiEffect
