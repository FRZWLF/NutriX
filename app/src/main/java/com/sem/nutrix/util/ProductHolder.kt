package com.sem.nutrix.util

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sem.nutrix.model.Product
import com.sem.nutrix.presentation.screens.auth.AuthViewModel
import com.sem.nutrix.presentation.screens.mealList.MealListViewModel
import com.sem.nutrix.ui.theme.Elevation
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProductHolder(
    product: Product,
    onDeleteProduct: () -> Unit,
    onClick: (String) -> Unit
){
    val localDensity = LocalDensity.current
    var componentHeight by remember { mutableStateOf(0.dp) }
    val viewModel: MealListViewModel = hiltViewModel()


    Row(
        modifier = Modifier.clickable(
            indication = null,
            interactionSource = remember{
                MutableInteractionSource()
            }
        ) { onClick(product._id.toHexString()) }
    ) {
        //Spacer(modifier = Modifier.width(20.dp))
        Surface(
            modifier = Modifier
                .clip(shape = Shapes().medium)
                .padding(bottom = 10.dp)
                .border(1.dp, Color.Gray, Shapes().medium)
                .onGloballyPositioned {
                    componentHeight = with(localDensity) { it.size.height.toDp() }
                },
            tonalElevation = Elevation.Level1
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                ) {
                    //ProductHeader(time = product.date.toInstant())
                    Text(
                        modifier = Modifier.padding(top = 14.dp, start = 14.dp, end = 14.dp),
                        text = product.title,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.SemiBold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(start = 14.dp, end = 14.dp),
                        text = product.kcal.toString() + " kcal",
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 14.dp),
                        text = product.amount,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Column(
                    modifier = Modifier.padding(end = 14.dp)
                ) {
                    IconButton(
                        onClick = {
                            viewModel.changeSelectedProductIdState(product._id.toHexString())
                            onDeleteProduct()
                                  },
                        colors = IconButtonColors(
                            containerColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                            contentColor = Color.Unspecified,
                            disabledContainerColor = Color.Unspecified,
                            disabledContentColor = Color.Unspecified)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Delete Product Icon",)
                    }
                }
            }
        }
    }
}


@SuppressLint("NewApi")
@Composable
fun ProductHeader(time: Instant) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("hh:mm a", Locale.getDefault())
            .withZone(ZoneId.systemDefault())
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = formatter.format(time),
            style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize)
        )
    }
}



@Composable
@Preview
fun ProductHolderPreview() {
    ProductHolder(product = Product().apply {
        title = "Kaugummi sticks"
        kcal = 11
        amount = "1 normale portion (3.4 g)"
//        description = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet."
    }, onDeleteProduct = {}, onClick = {})
}