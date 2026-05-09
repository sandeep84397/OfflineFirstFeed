package com.sandeep.offlinefirstfeed.di

import com.sandeep.kmpcore.di.coreNavigationModule
import com.sandeep.kmpcore.di.corePresentationModule
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module

fun initKoin(
    platformModules: List<Module> = emptyList(),
    appDeclaration: KoinApplication.() -> Unit = {},
) {
    startKoin {
        appDeclaration()
        modules(
            platformModules + listOf(
                coreNavigationModule,
                corePresentationModule,
                dataModule,
                domainModule,
                presentationModule,
            )
        )
    }
}
