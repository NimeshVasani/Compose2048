package org.nimesh.game.di

import createDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import org.nimesh.game.data.repositories.GameRepository
import org.nimesh.game.presentation.viewmodels.GameViewModel


actual val ViewModelModule = module {
    singleOf(::GameViewModel)
    singleOf(::GameRepository)
    single {
        createDataStore()
    }
}