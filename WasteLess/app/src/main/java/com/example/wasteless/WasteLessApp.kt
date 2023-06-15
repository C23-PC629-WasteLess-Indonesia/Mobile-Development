package com.example.wasteless

import android.content.Context
import android.os.Build
import android.transition.Scene
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.wasteless.preferences.UserPreferences
import com.example.wasteless.ui.navigation.NavigationItem
import com.example.wasteless.ui.navigation.Screen
import com.example.wasteless.ui.screen.auth.LoginScreen
import com.example.wasteless.ui.screen.auth.RegisterScreen
import com.example.wasteless.ui.screen.auth.SplashScreen
import com.example.wasteless.ui.screen.detail.DetailScreen
import com.example.wasteless.ui.screen.donate.DonateScreen
import com.example.wasteless.ui.screen.explore.ExploreScreen
import com.example.wasteless.ui.screen.home.HomeScreen
import com.example.wasteless.ui.screen.message.ConversationScreen
import com.example.wasteless.ui.screen.message.MessageScreen
import com.example.wasteless.ui.screen.profile.ProfileScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WasteLessApp(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    userPreferences: UserPreferences,
){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Splash.route && currentRoute != Screen.Login.route && currentRoute != Screen.Register.route && currentRoute != Screen.DetailFood.route && currentRoute != Screen.Donate.route && currentRoute != Screen.Conversation.route){
                Bottombar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Splash.route,
            modifier = modifier.padding(innerPadding)
        ){
            composable(Screen.Splash.route){
                SplashScreen {
                    if (userPreferences.getUser().isLogin){
                        navController.navigate(Screen.Home.route)
                    }else{
                        navController.navigate(Screen.Login.route)
                    }
                }
            }
            composable(Screen.Login.route){
                LoginScreen(
                    userPreferences = userPreferences,
                    navigateToHome = {
                    navController.navigate(Screen.Home.route)
                    },
                    navigateToRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                )
            }
            composable(Screen.Register.route){
                RegisterScreen(navigateToLogin = {
                    navController.navigate(Screen.Login.route)
                })
            }
            composable(Screen.Home.route){
                HomeScreen(userPreferences, navigateToDetail = { foodId ->
                    navController.navigate(Screen.DetailFood.createRoute(foodId))
                })
            }
            composable(
                route = Screen.DetailFood.route,
                arguments = listOf(navArgument("foodId"){ type = NavType.IntType})
            ){
                val id = it.arguments?.getInt("foodId") ?: -1
                DetailScreen(
                    foodId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    userPreferences = userPreferences,
                    navigateToConversationScreen = { receiverId ->
                        navController.navigate(Screen.Conversation.createRoute(receiverId))
                    }
                )
            }
            composable(
                route = Screen.DetailFoodFromExplore.route,
                arguments = listOf(navArgument("foodId"){ type = NavType.IntType })
            ){
                val id = it.arguments?.getInt("foodId") ?: -1
                DetailScreen(
                    foodId = id,
                    navigateBack = {
                        navController.navigateUp()
                    },
                    userPreferences = userPreferences,
                    navigateToConversationScreen = {  receiverId ->
                        navController.navigate(Screen.Conversation.createRoute(receiverId))
                    }
                )
            }
            composable(Screen.Explore.route){
                ExploreScreen(userPreferences = userPreferences, navigateToDetail = {
                    navController.navigate(Screen.DetailFood.createRoute(it))
                })
            }
            composable(Screen.Donate.route){
                DonateScreen(
                    onBackClick = {
                    navController.navigateUp()
                    },
                    userPreferences = userPreferences,
                    navigateToBack = {
                        navController.navigate(Screen.Home.route)
                    }
                )
            }
            composable(Screen.Messages.route){
                MessageScreen(userPreferences = userPreferences, navigateToConversation = { foodId ->
                    navController.navigate(Screen.Conversation.createRoute(foodId))
                })
            }
            composable(
                route = Screen.Conversation.route,
                arguments = listOf(navArgument("messageId"){ type = NavType.IntType })
            ){
                val id = it.arguments?.getInt("messageId") ?: -1
                ConversationScreen(
                    messageId = id,
                    userPreferences = userPreferences,
                    onBackClick = {
                        navController.navigateUp()
                    }
                )
            }
            composable(Screen.Profile.route){
                ProfileScreen(userPreferences = userPreferences, navigateToConversation = {
                    navController.navigate(Screen.Conversation.createRoute(it))
                })
            }
        }
    }

}

@Composable
private fun Bottombar(
    navController: NavHostController,
    modifier: Modifier = Modifier
){
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = "Home",
                icon = ImageVector.vectorResource(id = R.drawable.ic_outline_home_24),
                screen = Screen.Home
            ),
            NavigationItem(
                title = "Explore",
                icon = ImageVector.vectorResource(id = R.drawable.ic_outline_explore_24),
                screen = Screen.Explore
            ),
            NavigationItem(
                title = "Donate",
                icon = ImageVector.vectorResource(id = R.drawable.ic_round_add_circle_outline_24),
                screen = Screen.Donate
            ),
            NavigationItem(
                title = "Message",
                icon = ImageVector.vectorResource(id = R.drawable.ic_outline_message_24),
                screen = Screen.Messages
            ),
            NavigationItem(
                title = "Profile",
                icon = ImageVector.vectorResource(id = R.drawable.ic_outline_account_circle_24),
                screen = Screen.Profile
            )
        )
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = Color.White
        ) {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon ={
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(text = item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route){
                            popUpTo(navController.graph.findStartDestination().id){
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    })
            }
        }
    }
}