package com.sem.nutrix.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sem.nutrix.presentation.screens.mealList.CircularProgressBar
import java.time.LocalDate


@SuppressLint("NewApi")
@Composable
internal fun HomeTop(
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
                    text = "-------------",
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(onMenuClicked: () -> Unit){
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onMenuClicked) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Hamburger Menu Icon",
                    tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            },
            title = {
                Text(text = "Products")
            }

        )
    }