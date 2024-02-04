package com.sem.nutrix.presentation.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
internal fun BreakfastButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp)
            .padding(start = 25.dp, end = 25.dp)
            .background(
                color = Color.LightGray,
                shape = Shapes().medium
            )
            .border(1.dp, Color.Gray, Shapes().medium)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            modifier = Modifier
                .padding(start = 13.dp, end = 137.dp),
            text = "Breakfast".uppercase(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )
        Icon(
            modifier = Modifier
                .size(35.dp),
            tint = Color.Black,
            imageVector = Icons.Default.AddCircleOutline,
            contentDescription = "Add Product Icon")

    }
}

@Composable
internal fun LunchButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp)
            .padding(start = 25.dp, end = 25.dp)
            .background(
                color = Color.LightGray,
                shape = Shapes().medium
            )
            .border(1.dp, Color.Gray, Shapes().medium)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            modifier = Modifier
                .padding(start = 13.dp, end = 198.dp),
            text = "Lunch".uppercase(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )
        Icon(
            modifier = Modifier
                .size(35.dp),
            tint = Color.Black,
            imageVector = Icons.Default.AddCircleOutline,
            contentDescription = "Add Product Icon")

    }
}

@Composable
internal fun DinnerButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp)
            .padding(start = 25.dp, end = 25.dp)
            .background(
                color = Color.LightGray,
                shape = Shapes().medium
            )
            .border(1.dp, Color.Gray, Shapes().medium)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            modifier = Modifier
                .padding(start = 13.dp, end = 189.dp),
            text = "Dinner".uppercase(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )
        Icon(
            modifier = Modifier
                .size(35.dp),
            tint = Color.Black,
            imageVector = Icons.Default.AddCircleOutline,
            contentDescription = "Add Product Icon")

    }
}

@Composable
internal fun SnacksButton(
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp)
            .padding(start = 25.dp, end = 25.dp)
            .background(
                color = Color.LightGray,
                shape = Shapes().medium
            )
            .border(1.dp, Color.Gray, Shapes().medium)
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ){
        Text(
            modifier = Modifier
            .padding(start = 13.dp, end = 180.dp),
            text = "Snacks".uppercase(),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black

        )
        Icon(
            modifier = Modifier
            .size(35.dp),
            tint = Color.Black,
            imageVector = Icons.Default.AddCircleOutline,
            contentDescription = "Add Product Icon")

    }
}