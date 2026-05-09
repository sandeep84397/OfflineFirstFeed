package com.sandeep.offlinefirstfeed

import android.app.Application
import com.sandeep.offlinefirstfeed.di.androidPlatformModule
import com.sandeep.offlinefirstfeed.di.initKoin
import org.koin.android.ext.koin.androidContext

class OfflineFirstFeedApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin(platformModules = listOf(androidPlatformModule)) {
            androidContext(this@OfflineFirstFeedApp)
        }
    }
}
