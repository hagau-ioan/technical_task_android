package com.sliidepoc.ui.features.single

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.sliidepoc.ui.R
import com.sliidepoc.ui.navigation.NavPathRouteItem
import kotlinx.coroutines.delay

/**
 * Starting with API 31 will get access to the SplashScreen api support provided by google. Else
 * we need to build a hybrid version with compose based on the xml.
 *
 * @author Hagău Ioan
 * @since 2022.01.21
 */

@Composable
fun DisplaySplashScreen(
    @Suppress("UNUSED_PARAMETER") navController: NavHostController,
) {

    // TODO: Adding an android:windowBackground is not possible from Compose 08.2021. Need to use
    // a mixed version of xml and composable should be used. However with Android 12 we have
    // he API option of SplashScreen....

    val scale = remember {
        Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(durationMillis = 800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        // Customize the delay time
        delay(1000L)
        navController.navigate(NavPathRouteItem.Users.route)
    }

    // Image
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        // Change the logo
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value)
        )
    }
}
