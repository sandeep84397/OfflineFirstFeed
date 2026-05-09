package com.sandeep.offlinefirstfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sandeep.offlinefirstfeed.presentation.common.OfflineFirstFeedTheme
import com.sandeep.offlinefirstfeed.presentation.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OfflineFirstFeedTheme {
                AppNavHost()
            }
        }
    }
}
