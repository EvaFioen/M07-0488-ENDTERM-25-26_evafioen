package com.example.m07_0488_endterm_pr01_evafioen.ui.navigation

object Routes {
    const val SPLASH = "splash"
    const val LOGIN = "login"
    const val HOME = "home"
    const val DETAIL = "detail/{rocketId}"
    fun createDetailRoute(rocketId: String) = "detail/$rocketId"
}