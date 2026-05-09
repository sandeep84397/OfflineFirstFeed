package com.sandeep.kmpcore.di

import kotlinx.coroutines.CoroutineExceptionHandler
import org.koin.dsl.module

val corePresentationModule = module {
    single<CoroutineExceptionHandler> {
        CoroutineExceptionHandler { _, throwable ->
            println("[BaseViewModel] Uncaught: ${throwable::class.simpleName}: ${throwable.message}")
        }
    }
}
