package com.sandeep.kmpcore.di

import com.sandeep.kmpcore.navigation.AppNavigator
import com.sandeep.kmpcore.navigation.Navigator
import org.koin.dsl.module

val coreNavigationModule = module {
    single { AppNavigator() }
    single<Navigator> { get<AppNavigator>() }
}
