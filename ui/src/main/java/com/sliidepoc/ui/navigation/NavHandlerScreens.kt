package com.sliidepoc.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sliidepoc.ui.R
import com.sliidepoc.ui.components.DrawContentScreenState
import com.sliidepoc.ui.features.single.DisplaySplashScreen
import com.sliidepoc.ui.features.users.DisplayUsersScreen

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.21
 */
@Composable
fun ComposeNavigation(navController: NavHostController, drawContentState: DrawContentScreenState) {
    NavHost(navController, startDestination = NavPathRouteItem.Splash.route) {
        composable(NavPathRouteItem.Splash.route) {
            DisplaySplashScreen(navController = navController)
        }
        composable(NavPathRouteItem.Users.route) {
            DisplayUsersScreen(hiltViewModel(), navController, drawContentState)
        }
    }
}

sealed class NavPathRouteItem(
    val route: String,
    val icon: ImageVector,
    val titleRes: Int
) {

    object Splash :
        NavPathRouteItem("splash_screen", Icons.Filled.Star, R.string.menu_item_title_splash)

    object Users :
        NavPathRouteItem("users", Icons.Filled.List, R.string.menu_item_title_users)
}
