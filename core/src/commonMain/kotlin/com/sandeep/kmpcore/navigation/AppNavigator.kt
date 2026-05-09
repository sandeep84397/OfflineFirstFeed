package com.sandeep.kmpcore.navigation

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

class AppNavigator : Navigator {

    private var navController: NavHostController? = null

    fun attach(navController: NavHostController) {
        this.navController = navController
    }

    fun detach() {
        navController = null
    }

    override fun navigate(route: Route) {
        navController?.navigate(route)
    }

    override fun popBack() {
        navController?.popBackStack()
    }

    fun navigate(route: Route, builder: NavOptionsBuilder.() -> Unit) {
        navController?.navigate(route, builder)
    }
}
