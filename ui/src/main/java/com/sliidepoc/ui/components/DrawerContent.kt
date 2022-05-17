package com.sliidepoc.ui.components

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.sliidepoc.common.utils.formater.StringUtils
import com.sliidepoc.ui.R
import com.sliidepoc.ui.navigation.ComposeNavigation
import com.sliidepoc.ui.navigation.NavPathRouteItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *
 *
 * @author Hagău Ioan
 * @since 2022.01.22
 */
@Composable
fun DrawerContent(context: Context) {

    val drawContentState: DrawContentScreenState = rememberDrawContentScreenStateHolder(
        scaffoldState = rememberScaffoldState(
            rememberDrawerState(DrawerValue.Closed)
        ),
        selectedItem = rememberSaveable { mutableStateOf(NavPathRouteItem.Users.route) },
        title = rememberSaveable { mutableStateOf(NavPathRouteItem.Users.titleRes) }
    )

    val navController = rememberNavController()

    Surface(color = MaterialTheme.colorScheme.onSurfaceVariant) {
        Scaffold(
            scaffoldState = drawContentState.scaffoldState,
            drawerBackgroundColor = Color.Transparent,
            drawerElevation = 0.dp,
            drawerShape = customShape(),
            drawerContent = {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.onPrimary)
                        .fillMaxWidth(0.40f)
                        .fillMaxHeight()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = context.getString(R.string.app_name),
                            style = MaterialTheme.typography.displaySmall,
                            textAlign = TextAlign.Left
                        )
                    }
                    Column(
                        modifier = Modifier
                    ) {
                        AddRowMenuItem(
                            drawContentState, navController, NavPathRouteItem.Users
                        )
                    }
                }
            },

            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = context.getString(drawContentState.getTopMenuTitle().value),
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },

                    navigationIcon = {
                        IconButton(onClick = {
                            drawContentState.coroutinesScope.launch {
                                drawContentState.scaffoldState.drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Filled.Menu,
                                contentDescription = StringUtils.EMPTY_STRING,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    },

                    backgroundColor = MaterialTheme.colorScheme.primary,
                    elevation = AppBarDefaults.TopAppBarElevation
                )
            },

            content = {
                ComposeNavigation(navController = navController, drawContentState)
            }
        )
    }
}

@Composable
fun customShape() = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Rectangle(
            Rect(
                left = 0f,
                top = 0f,
                right = size.width * 2 / 3,
                bottom = size.height
            )
        )
    }
}

@Composable
fun AddRowMenuItem(
    drawContentState: DrawContentScreenState,
    navController: NavHostController,
    route: NavPathRouteItem
) {
    Row(
        modifier = Modifier
            .clickable {
                drawContentState.coroutinesScope.launch {
                    drawContentState.scaffoldState.drawerState.close()
                    drawContentState.getTopMenuTitle().value = route.titleRes
                    drawContentState.getMenuItem().value = route.route
                    navController.navigate(
                        route.route,
                        navOptions {
                            if (route == NavPathRouteItem.Users) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = false
                                }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    )
                }
            }
            .background(
                if (drawContentState.getMenuItem().value == route.route) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(0.dp)
            )
            .fillMaxWidth()
            .padding(8.dp)
            .padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            route.icon,
            contentDescription = StringUtils.EMPTY_STRING,
            tint = if (drawContentState.getMenuItem().value == route.route) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
        )
        Text(
            modifier = Modifier.padding(top = 2.dp),
            text = stringResource(route.titleRes),
            style = MaterialTheme.typography.labelLarge,
            color = if (drawContentState.getMenuItem().value == route.route) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
        )
    }
}

data class DrawContentScreenState(
    val coroutinesScope: CoroutineScope,
    val scaffoldState: ScaffoldState,
    private var selectedItem: MutableState<String>,
    private var title: MutableState<Int>
) {
    fun setMenuItem(item: String) {
        selectedItem.value = item
    }

    fun setTopMenuTitle(@StringRes itemRes: Int) {
        title.value = itemRes
    }

    fun getMenuItem(): MutableState<String> {
        return selectedItem
    }

    fun getTopMenuTitle(): MutableState<Int> {
        return title
    }
}

@Composable
fun rememberDrawContentScreenStateHolder(
    coroutinesScope: CoroutineScope = rememberCoroutineScope(),
    scaffoldState: ScaffoldState,
    selectedItem: MutableState<String>,
    title: MutableState<Int>
) = remember {
    DrawContentScreenState(
        coroutinesScope = coroutinesScope,
        scaffoldState = scaffoldState,
        selectedItem = selectedItem,
        title = title
    )
}
