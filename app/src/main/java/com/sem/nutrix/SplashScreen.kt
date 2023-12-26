package com.sem.nutrix

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
@Composable
fun Splash(
    navigateNext: () -> Unit
) {
    AnimatedSplashScreen {
        navigateNext()
    }
}
@Composable
fun AnimatedSplashScreen(
    onAnimationEnd: @Composable () -> Unit
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.splash_screen_520w))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    LaunchedEffect(progress) {

    }
    if (progress >= 1f) {
        onAnimationEnd()
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            modifier = Modifier.fillMaxWidth()
                .scale(1.2f),
            composition = composition,
            progress = { progress }
        )
    }

}