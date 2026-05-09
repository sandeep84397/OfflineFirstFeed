package com.sandeep.offlinefirstfeed

import androidx.compose.ui.window.ComposeUIViewController
import com.sandeep.offlinefirstfeed.di.initKoin
import com.sandeep.offlinefirstfeed.di.iosPlatformModule
import com.sandeep.offlinefirstfeed.presentation.common.OfflineFirstFeedTheme
import com.sandeep.offlinefirstfeed.presentation.navigation.AppNavHost
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    initKoin(platformModules = listOf(iosPlatformModule))
    return ComposeUIViewController {
        OfflineFirstFeedTheme {
            AppNavHost()
        }
    }
}
