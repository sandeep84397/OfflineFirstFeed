package com.sandeep.kmpcore.navigation

interface Navigator {
    fun navigate(route: Route)
    fun popBack()
}
