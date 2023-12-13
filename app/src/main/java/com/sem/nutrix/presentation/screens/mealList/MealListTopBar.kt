package com.sem.nutrix.presentation.screens.mealList

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MealListTopBar(
//    scrollBehavior: TopAppBarScrollBehavior,
    onDeleteAllClicked: () -> Unit,
    navigateBackToHome: () -> Unit
) {
    TopAppBar(
//        scrollBehavior = scrollBehavior,
        navigationIcon = {
            IconButton(onClick = navigateBackToHome) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back Icon",
                    tint = MaterialTheme.colorScheme.background
                )
            }
        },
        title = { },
        actions = {
            IconButton(onClick = onDeleteAllClicked) { //unused?
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete All Logo",
                    tint = MaterialTheme.colorScheme.background
                )
            } },
        colors = TopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Unspecified,
            navigationIconContentColor = Color.Black,
            titleContentColor = Color.Black,
            actionIconContentColor = Color.Black
        )
    )
}

@Composable
fun CircularProgressBar(
    percentage: Float,
    number: Int,
    fontSize: TextUnit = 40.sp,
    radius: Dp = 70.dp,
    color: Color = MaterialTheme.colorScheme.background,
    strokeWidth: Dp = 7.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
) {
    var animationPlayed by remember {
        mutableStateOf(false)
    }

    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = "Calorie-Tracker"
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    Box(
        modifier = Modifier.size(radius * 2f),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color.copy(alpha = 0.38f),
                -90f,
                360F,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Canvas(modifier = Modifier.size(radius * 2f)) {
            drawArc(
                color = color,
                -90f,
                360 * curPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
            )
        }
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = (curPercentage.value * number).toInt().toString(),
                color = MaterialTheme.colorScheme.background,
                fontSize = fontSize,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "KCAL",
                color = MaterialTheme.colorScheme.background,
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@SuppressLint("NewApi")
@Composable
internal fun MealListTop(
    value: Int
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(290.dp),
        color = MaterialTheme.colorScheme.secondaryContainer
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer,
                            MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.6f)
                        ),
                        startX = 0f,
                        endX = Float.POSITIVE_INFINITY
                    )
                )
                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressBar(
                    percentage = value.toFloat() / 1000,
                    number = 1000
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 25.dp, top = 15.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Breakfast",
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = LocalDate.now().dayOfMonth.toString() + " " + LocalDate.now().month + " " + LocalDate.now().year,
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
    }
}