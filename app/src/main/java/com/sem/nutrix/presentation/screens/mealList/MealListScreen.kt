package com.sem.nutrix.presentation.screens.mealList

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sem.nutrix.data.repository.Products
import com.sem.nutrix.model.RequestState
import com.sem.nutrix.presentation.screens.auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
internal fun MealListScreen(
    products: Products,
    uiState: UiState,
    onDeleteProduct: () -> Unit,
    onDeleteAllClicked: () -> Unit,
    navigateToWrite: () -> Unit,
    navigateToWriteWithArgs: (String) -> Unit,
    navigateBackToHome: () -> Unit
) {
    val test: AuthViewModel = viewModel()
//    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MealListTopBar(
//                scrollBehavior = scrollBehavior,
                onDeleteAllClicked = onDeleteAllClicked,
                navigateBackToHome = navigateBackToHome
            )
        },
        content = {
            LazyColumn {
                item {
                    MealListTop(
                        value = test.totalKcal
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    MealListAddButton(
                        onClick = navigateToWrite
                    )
                    when (products) {
                        is RequestState.Success -> {
                            MealListContent(
                                productsNote = products.data,
                                onDeleteProduct = onDeleteProduct,
                                onClick = navigateToWriteWithArgs
                            )
                        }

                        is RequestState.Error -> {
                            EmptyPage(
                                title = "Error",
                                subtitle = "${products.error.message}"
                            )
                        }

                        is RequestState.Loading -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }

                        else -> {}
                    }
                    MealListNutritionTable(
                        value = test.totalKcal
                    )
                }

            }

        }
    )
}