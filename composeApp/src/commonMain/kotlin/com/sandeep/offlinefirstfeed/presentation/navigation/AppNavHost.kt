package com.sandeep.offlinefirstfeed.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.sandeep.kmpcore.navigation.AppNavigator
import com.sandeep.offlinefirstfeed.presentation.createpost.CreatePostRoute
import com.sandeep.offlinefirstfeed.presentation.feed.FeedRoute as FeedRouteComposable
import com.sandeep.offlinefirstfeed.presentation.postdetail.PostDetailRoute
import org.koin.compose.koinInject

@Composable
fun AppNavHost(navController: NavHostController = rememberNavController()) {
    val appNavigator = koinInject<AppNavigator>()

    DisposableEffect(navController) {
        appNavigator.attach(navController)
        onDispose { appNavigator.detach() }
    }

    NavHost(navController = navController, startDestination = FeedRoute) {
        composable<FeedRoute> {
            FeedRouteComposable()
        }
        composable<PostDetailRoute> { backStackEntry ->
            val args = backStackEntry.toRoute<PostDetailRoute>()
            PostDetailRoute(
                postId = args.postId,
                onBack = { navController.popBackStack() },
            )
        }
        composable<CreatePostRoute> {
            CreatePostRoute(onBack = { navController.popBackStack() })
        }
    }
}
