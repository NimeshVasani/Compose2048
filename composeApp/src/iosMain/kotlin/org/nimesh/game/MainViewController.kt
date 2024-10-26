package org.nimesh.game

import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.nimesh.game.di.ViewModelModule

fun MainViewController() = ComposeUIViewController {
    startKoin {
        modules(ViewModelModule)
    }
    App()
}
