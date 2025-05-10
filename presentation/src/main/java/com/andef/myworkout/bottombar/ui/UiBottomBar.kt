package com.andef.myworkout.bottombar.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.andef.myworkout.bottombar.items.UiBottomBarItem
import com.andef.myworkout.navigation.Screen
import com.andef.myworkout.ui.theme.Black
import com.andef.myworkout.ui.theme.White
import com.andef.myworkout.ui.utils.slideInUp
import com.andef.myworkout.ui.utils.slideOutDown

@Composable
fun UiBottomBar(navHostController: NavHostController) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    AnimatedContent(
        targetState = isNeedToShowBottomBar(currentDestination),
        transitionSpec = { slideInUp.togetherWith(slideOutDown) }
    ) { state ->
        if (state) {
            NavigationBarContent(
                navHostController = navHostController,
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
private fun NavigationBarContent(
    navHostController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(topEnd = 28.dp, topStart = 28.dp),
                ambientColor = Black,
                spotColor = Black
            )
            .border(
                width = 1.dp,
                color = Black.copy(alpha = 0.08f),
                shape = RoundedCornerShape(topEnd = 28.dp, topStart = 28.dp)
            ),
        containerColor = White,
        contentColor = Black
    ) {
        items.forEach { item ->
            NavigationBarItemContent(
                item = item,
                navHostController = navHostController,
                currentDestination = currentDestination
            )
        }
    }
}

@Composable
private fun RowScope.NavigationBarItemContent(
    item: UiBottomBarItem,
    navHostController: NavHostController,
    currentDestination: NavDestination?
) {
    NavigationBarItem(
        colors = navigationBarItemColors(),
        icon = {
            Icon(
                painter = painterResource(item.iconResId),
                contentDescription = stringResource(item.contentDescriptionResId),
                tint = Black
            )
        },
        label = { Text(text = stringResource(item.titleResId), color = Black, fontSize = 13.sp) },
        selected = currentDestination?.route == item.route,
        onClick = {
            navHostController.navigate(item.route) {
                popUpTo(Screen.CalendarScreen.MainScreen.route) {
                    saveState = true
                }
                restoreState = true
                launchSingleTop = true
            }
        },
        alwaysShowLabel = false
    )
}

private fun isNeedToShowBottomBar(currentDestination: NavDestination?): Boolean {
    return currentDestination?.route in listOf(
        Screen.CalendarScreen.MainScreen.route,
        Screen.ExercisesScreen.MainScreen.route,
        Screen.AccountScreen.route
    )
}

@Composable
private fun navigationBarItemColors() = NavigationBarItemDefaults.colors(
    selectedIconColor = Black,
    selectedTextColor = Black,
    indicatorColor = Color.Transparent,
    unselectedIconColor = Black,
    unselectedTextColor = Black,
    disabledIconColor = Black,
    disabledTextColor = Black
)

private val items = UiBottomBarItem.getItemsList()