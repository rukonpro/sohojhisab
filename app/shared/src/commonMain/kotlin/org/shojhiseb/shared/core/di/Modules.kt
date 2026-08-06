package org.shojhiseb.shared.core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.shojhiseb.shared.feature_ledger.data.repository.LedgerRepositoryImpl
import org.shojhiseb.shared.feature_ledger.domain.repository.LedgerRepository
import org.shojhiseb.shared.feature_ledger.domain.usecase.DeleteLedgerUseCase
import org.shojhiseb.shared.feature_ledger.domain.usecase.GetLedgersUseCase
import org.shojhiseb.shared.feature_ledger.domain.usecase.InsertLedgerUseCase
import org.shojhiseb.shared.feature_ledger.domain.usecase.LedgerUseCases
import org.shojhiseb.shared.feature_transaction.data.repository.CategoryRepositoryImpl
import org.shojhiseb.shared.feature_transaction.data.repository.TransactionRepositoryImpl
import org.shojhiseb.shared.feature_transaction.domain.repository.CategoryRepository
import org.shojhiseb.shared.feature_transaction.domain.repository.TransactionRepository
import org.shojhiseb.shared.feature_transaction.domain.usecase.CategoryUseCases
import org.shojhiseb.shared.feature_transaction.domain.usecase.DeleteCategoryUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.DeleteTransactionUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.GetCategoriesUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.GetTransactionsUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.InsertCategoryUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.InsertTransactionUseCase
import org.shojhiseb.shared.feature_transaction.domain.usecase.TransactionUseCases
import org.shojhiseb.shared.feature_transaction.domain.validation.TransactionValidator
import org.shojhiseb.shared.feature_transaction.presentation.TransactionScreenModel
import org.shojhiseb.shared.feature_dashboard.presentation.DashboardScreenModel
import org.shojhiseb.shared.feature_analytics.presentation.AnalyticsScreenModel
import org.shojhiseb.shared.feature_ledger.presentation.LedgerScreenModel
import org.shojhiseb.shared.core.location.LocationService

import com.russhwolf.settings.Settings
import org.shojhiseb.shared.core.export.ExportManager
import org.shojhiseb.shared.feature_settings.data.UserSettingsRepository
import org.shojhiseb.shared.feature_settings.data.UserSettingsRepositoryImpl
import org.shojhiseb.shared.feature_settings.presentation.SettingsScreenModel

val repositoryModule = module {
    // single<Settings> { Settings() } // Must be provided from platform module
    singleOf(::UserSettingsRepositoryImpl) bind UserSettingsRepository::class
    singleOf(::TransactionRepositoryImpl) bind TransactionRepository::class
    singleOf(::CategoryRepositoryImpl) bind CategoryRepository::class
    singleOf(::LedgerRepositoryImpl) bind LedgerRepository::class
}

val useCaseModule = module {
    singleOf(::TransactionValidator)
    singleOf(::ExportManager)
    singleOf(::LocationService)
    single { GetTransactionsUseCase(get()) }
    single { InsertTransactionUseCase(get(), get()) }
    single { DeleteTransactionUseCase(get()) }
    single { TransactionUseCases(get(), get(), get()) }

    single { GetCategoriesUseCase(get()) }
    single { InsertCategoryUseCase(get()) }
    single { DeleteCategoryUseCase(get()) }
    single { CategoryUseCases(get(), get(), get()) }

    single { GetLedgersUseCase(get()) }
    single { InsertLedgerUseCase(get()) }
    single { DeleteLedgerUseCase(get()) }
    single { LedgerUseCases(get(), get(), get()) }
}

val screenModelModule = module {
    factoryOf(::TransactionScreenModel)
    factoryOf(::DashboardScreenModel)
    factoryOf(::AnalyticsScreenModel)
    factoryOf(::LedgerScreenModel)
    factoryOf(::SettingsScreenModel)
}

fun initKoin(appModule: Module) {
    startKoin {
        modules(
            appModule, // Platform specific database module
            repositoryModule,
            useCaseModule,
            screenModelModule
        )
    }
}
