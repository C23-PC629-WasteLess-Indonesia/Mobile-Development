package com.example.wasteless.ui.navigation

sealed class Screen(val route: String){
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Explore: Screen("explore")
    object Donate: Screen("donate")
    object Messages: Screen("messages")
    object Conversation: Screen("messages/{messageId}"){
        fun createRoute(messageId: Int) = "messages/$messageId"
    }
    object Profile: Screen("profile")
    object DetailFood: Screen("home/{foodId}"){
        fun createRoute(foodId: Int) = "home/$foodId"
    }
    object DetailFoodFromExplore: Screen("explore/{foodId}"){
        fun createRoute(foodId: Int) = "explore/$foodId"
    }
}
