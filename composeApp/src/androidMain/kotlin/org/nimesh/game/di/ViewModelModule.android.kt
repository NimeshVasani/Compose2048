package org.nimesh.game.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.nimesh.game.createDataStore
import org.nimesh.game.presentation.viewmodels.GameViewModel
import org.nimesh.game.data.repositories.GameRepository

actual val ViewModelModule = module {
    singleOf(::GameViewModel)
    singleOf(::GameRepository)
    single {
        createDataStore(androidContext())
    }
}