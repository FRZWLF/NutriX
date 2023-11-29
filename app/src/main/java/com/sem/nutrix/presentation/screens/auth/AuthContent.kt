package com.sem.nutrix.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sem.nutrix.R
import com.sem.nutrix.presentation.components.ButtonComp
import com.sem.nutrix.presentation.components.Checkbox
import com.sem.nutrix.presentation.components.ClickableLoginText
import com.sem.nutrix.presentation.components.DividerText
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.components.MyTextField
import com.sem.nutrix.presentation.components.PasswordMyTextField
import com.sem.nutrix.presentation.components.Registration
import okhttp3.Route

@Composable
fun AuthContent(
    loadingState: Boolean,
    onButtonClicked: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth()
                .padding(all = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
//                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Registration()

            }
            Spacer(modifier = Modifier.height(10.dp))
            Column(
//                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MyTextField(
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.user_profile)
                )
                Spacer(modifier = Modifier.height(5.dp))
                MyTextField(
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.user_profile)
                )
                Spacer(modifier = Modifier.height(5.dp))
                MyTextField(
                        labelValue = stringResource(id = R.string.email),
                        painterResource = painterResource(id = R.drawable.envelope_icon)
                )
                Spacer(modifier = Modifier.height(5.dp))
                PasswordMyTextField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock)
                )
                Checkbox(
                    value = stringResource(id = R.string.terms_and_conditions),
                    onTextSelected = {
                        //er will router machen zu einer Terms Seite?!
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                ButtonComp(value = stringResource(id = R.string.register))
                Spacer(modifier = Modifier.height(5.dp))
                DividerText()
                Spacer(modifier = Modifier.height(5.dp))
            }

            Column(
                modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.Top
            ) {
                GoogleButton(
                    loadingState = loadingState,
                    onClick = onButtonClicked,
                    primaryText = stringResource(id = R.string.google_sign_up)
                )
            }
            ClickableLoginText(
                onTextSelected = {
                    //Router zu Login-Screen
                }
            )
        }
    }
}

@Preview
@Composable
fun AuthContPreview() {
    AuthContent(loadingState = false) {
        
    }
}

