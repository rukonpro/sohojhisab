# PROJECT OVERVIEW
**Project:** Cross-Platform Personal Finance & Ledger App (KMP)
**Role:** Expert Principal Kotlin Multiplatform (KMP) Architect
**Goal:** Build a production-grade (10/10), bilingual (English & Bengali), offline-first cross-platform application (Android, iOS, Desktop). Follow the strict 8-Phase execution structure. STOP after each step and wait for the user's confirmation.
**Git Strategy (CRITICAL):** For EVERY step, you MUST create a new Git branch, commit the changes, push it to `https://github.com/rukonpro/sohojhisab`, and then rebase and merge it into the `main` branch. Always follow this Git workflow.

## 1. ENTERPRISE KMP TECH STACK & ARCHITECTURE
* **Dependency Management:** Version Catalog (`libs.versions.toml`)
* **Architecture Pattern:** Strict Clean Architecture with Feature Modularization (UI Layer -> ViewModel -> UseCase -> Repository -> SQLDelight/Settings)
* **UI Framework:** Compose Multiplatform (Shared UI) with Accessibility (a11y) semantics and **Compose Previews** (`@Preview`) for rapid UI iteration.
* **UI State Management:** MVI (Model-View-Intent) / UDF (Unidirectional Data Flow) handling `UiState` and `UiEvent`.
* **Navigation:** Voyager or Jetpack Navigation for KMP (Strictly typed routes)
* **Image Loading:** Coil 3 (KMP supported) for caching profile/category images
* **Localization & Resources:** JetBrains Compose Multiplatform Resources (for English/Bengali strings, fonts, drawables)
* **Database:** SQLDelight (Coroutines Flow integration & built-in Migration strategy)
* **Preferences & Security:** Multiplatform Settings (DataStore alternative) & SecureStorage (expect/actual wrapper around Android Keystore / iOS Keychain)
* **Logging, Crash Reporting & Performance:** Napier (Multiplatform logging), expect/actual for Firebase Crashlytics, and LeakCanary (in androidMain).
* **Data Flow & Sync:** `StateFlow<UiState>` for UI, returning `Resource<T>` wrapper from Repositories. Repository acts as "Single Source of Truth" with a Sync Layer for future cloud sync. Centralized `CoroutineExceptionHandler` to prevent silent crashes.
* **Data Integrity:** Domain Models strictly separated from DB Entities via Mappers. Soft delete (`deletedAt`) and audit fields implemented across entities.
* **Dependency Injection:** Koin (Segmented into `coreModule`, `featureModules`, `platformModules`)

## 2. SCALABLE KMP MULTI-MODULE STRUCTURE (WITH FEATURE MODULES)
```text
composeApp/
    androidMain/ (Includes LeakCanary, Firebase Crashlytics init)
    iosMain/
    desktopMain/
    commonMain/ 
        ui/ (Screens, Navigation, Composables, DesignSystem, Previews)
        viewmodel/ (MVI ViewModels, StateFlow, Intents)
shared/
    src/
        commonMain/kotlin/
            core/ (Resource wrapper, Dispatchers, Error Handlers, Logger, SyncManager, SecureStorage)
            database/ (SQLDelight DAOs, Migrations)
            feature_transaction/
                domain/ (Models, UseCases, Repository Interfaces)
                data/ (Implementations, Mappers, Validation)
            feature_ledger/
                domain/
                data/
            feature_settings/
                domain/
                data/
        androidMain/kotlin/ (Expect/Actual implementations: WorkManager, Keystore)
        iosMain/kotlin/ (Expect/Actual implementations: BGTaskScheduler, Keychain)
        desktopMain/kotlin/
```

## 3. DOMAIN MODELS & ENUMS
* **TransactionType Enum:** Income, Expense, Transfer, LoanGiven, LoanTaken
* **LedgerStatus Enum:** Pending, Paid, Received, Cancelled
* **Audit Fields:** All database entities must include `createdAt`, `updatedAt`, and `deletedAt` (Soft Delete).
* **Category Domain:** id, nameResId, type, icon, color, priority, isDefault.

## 4. EXECUTION PROMPTS (MICRO-STEPS FOR AI)

### --- PHASE 1: KMP PROJECT FOUNDATION & CORE CONFIG ---
**Step 1: Version Catalog, KMP Setup & Feature Modules**
> "Set up the libs.versions.toml with KMP plugins, Compose Multiplatform, Coroutines, SQLDelight, Koin, Napier, Coil 3, Compose Resources, and LeakCanary (Android). Configure the KMP multi-module structure with feature-based segmentation in the shared module."

**Step 2: Shared Theme, MVI Setup & Error Handling**
> "Create common Color.kt and Theme.kt in commonMain. Build the custom Design System. Setup JetBrains `@Preview` annotation support for visualizing Composables. Create base interfaces for MVI (UiState, UiEvent, UiEffect) and set up a centralized CoroutineExceptionHandler."

### --- PHASE 2: SHARED DATA, DOMAIN & VALIDATION LAYER ---
**Step 3: Entities, Models & Database Migrations**
> "Create SQLDelight schema with audit fields (createdAt, deletedAt) and establish migration (.sqm) strategy. Create clean Domain Models for Transaction, Category, and Ledger. Write bidirectional Mapper functions."

**Step 4: SQLDelight DAOs, Repositories & Sync Layer**
> "Set up SQLDelight DAOs. Implement the Resource<T> wrapper. Build Repository implementations acting as the 'Single Source of Truth' and create a base interface for the SyncManager."

**Step 5: Validation, UseCases & Domain Tests**
> "Create Business Validation logic (e.g., TransactionValidator). Create independent UseCases. Write common Unit Tests for Validators and UseCases using Kotlin Test."

### --- PHASE 3: COMMON NAVIGATION, RESOURCES & APP SHELL ---
**Step 6: Compose Multiplatform Navigation & i18n**
> "Set up the root Scaffold with a Bottom Navigation Bar using Voyager or Jetpack Navigation in commonMain. Configure JetBrains Compose Resources for English and Bengali localization. Write `@Preview` functions for the Navigation Bar and App Shell."

**Step 7: MVI ViewModels & Presentation Tests**
> "Create shared ViewModels injecting UseCases via Koin. Implement the `onEvent` MVI pattern to handle user intents and expose UI states via `StateFlow`. Write Unit Tests for ViewModels."

### --- PHASE 4: CORE TRANSACTIONS & BACKGROUND TASKS ---
**Step 8: Transaction List, Pagination & Accessibility**
> "Implement the Dashboard 'Transactions' tab with Search, Filter, and Sort. Use Coil 3 for category icons. Ensure all Compose UI elements have proper accessibility (a11y) semantics and include `@Preview` for both Light and Dark modes."

**Step 9: Dynamic Add Transaction (Bottom Sheet)**
> "Build the ModalBottomSheet triggered by a FAB in common Compose. Apply dynamic UI logic based on TransactionType. Include comprehensive `@Preview` setup to visualize different transaction states."

**Step 10: Background Tasks & Local Notifications**
> "Implement expect/actual declarations for scheduled recurring transactions and Local Notifications (mapping to WorkManager/AlarmManager on Android, BGTaskScheduler/UNUserNotificationCenter on iOS)."

### --- PHASE 5: ADVANCED ANALYTICS & LEDGER ---
**Step 11: Analytics Dashboard**
> "Implement the 'Analytics' tab. Use a KMP-compatible chart library to display Weekly/Monthly Trends, Cash Flow, and Category Breakdowns. Include `@Preview` data mocks for the charts."

**Step 12: Ledger Screen**
> "Build the Ledger Screen (Tabs for To Pay / To Receive). Connect it to LedgerViewModel using MVI and add Split Bill UI features."

### --- PHASE 6: SETTINGS, SECURITY & MULTIPLATFORM BACKUP ---
**Step 13: Multiplatform Settings UI**
> "Implement UserSettingsRepository using Multiplatform Settings. Build the Settings Screen to toggle Language, Theme, Currency, and Date Format. Include `@Preview` for the Settings layout."

**Step 14: Secure Storage & Crashlytics**
> "Implement SecureStorage via expect/actual (Android Keystore / iOS Keychain) for App Lock functionality. Add expect/actual setup for Firebase Crashlytics to track production issues."

**Step 15: Export & Backup**
> "Implement feature to export transaction reports and backup data to JSON/CSV using KMP file I/O libraries (like okio)."

### --- PHASE 7: PREMIUM UI POLISH & UX ---
**Step 16: Glassmorphism & Neumorphism**
> "Refine all shared Compose UI components. Apply Glassmorphism blur effects to Dialogs/Sheets. Add soft Neumorphic gradients and shadows to the Dashboard Balance Card. Validate effects heavily using `@Preview`."

**Step 17: Animations & Empty States**
> "Integrate Lottie/Kamel animations for Empty UI States. Animate the Balance text counting up. Add subtle haptic feedback using expect/actual."

### --- PHASE 8: ADVANCED FEATURES & CI/CD ---
**Step 18: Smart Data Entry**
> "Add 'Quick Template' chips to the Dashboard for 1-click entries. Implement UI for template management."

**Step 19: Location Tagging**
> "Integrate basic location fetching via expect/actual to save latitude/longitude when a transaction is saved."

**Step 20: UI Testing & CI/CD Setup**
> "Write Compose UI tests for critical screens. Set up a basic GitHub Actions workflow file to run unit tests and verify builds for Android and iOS on every commit."
