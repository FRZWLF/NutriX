package com.sem.nutrix.presentation.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sem.nutrix.R
import com.sem.nutrix.presentation.components.GoogleButton
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sem.nutrix.presentation.screens.auth.AuthViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    loadingState: Boolean,
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
    onSignOutClicked: () -> Unit,
    onSyncClicked: () -> Unit,
    navigateToMeal: () -> Unit
) {
    NavigationDrawer(
        drawerState = drawerState,
        onSignOutClicked = onSignOutClicked,
        loadingState = loadingState,
        onSyncClicked = onSyncClicked
    ) {
        val test: AuthViewModel = viewModel()
        Scaffold(
                topBar = {
                    HomeTopBar(onMenuClicked = onMenuClicked)
                },
                content = {
                Column(
                ){
                    HomeTop(
                        value = test.totalKcal
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    BreakfastButton(
                        onClick = navigateToMeal
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    LunchButton(
                        onClick = navigateToMeal
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    DinnerButton(
                        onClick = navigateToMeal
                    )
                    Spacer(modifier = Modifier.height(30.dp))
                    SnacksButton(
                        onClick = navigateToMeal
                    )
                }
        })
    }
}

@Composable
fun NavigationDrawer(
    loadingState: Boolean,
    onSyncClicked: () -> Unit,
    drawerState: DrawerState,
    onSignOutClicked: () -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                content = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ){
                        Image(
                            modifier = Modifier.size(250.dp),
                            painter = painterResource(id = R.drawable.logologo),
                            contentDescription = "Logo Image"
                        )
                    }
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                GoogleButton(
                                    loadingState = loadingState,
                                    onClick = onSyncClicked,
                                    primaryText = stringResource(id = R.string.google_sign_up)
                                )

                            }
                        },
                        selected = false,
                        onClick = onSyncClicked
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    NavigationDrawerItem(
                        label = {
                            Row(modifier = Modifier.padding(horizontal = 12.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.google_app_logo),
                                    contentDescription = "Google Logo"
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "Sign Out",
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        },
                        selected = false,
                        onClick = onSignOutClicked
                    )
                }
            )

        },
        content = content
    )
}