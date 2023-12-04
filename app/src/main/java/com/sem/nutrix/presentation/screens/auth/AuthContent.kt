package com.sem.nutrix.presentation.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sem.nutrix.R
import com.sem.nutrix.presentation.components.ButtonComp
import com.sem.nutrix.presentation.components.Checkbox
import com.sem.nutrix.presentation.components.ClickableLoginText
import com.sem.nutrix.presentation.components.DividerText
import com.sem.nutrix.presentation.components.EmailMyTextField
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.components.MyTextField
import com.sem.nutrix.presentation.components.PasswordMyTextField
import com.sem.nutrix.presentation.components.Registration
//var Email: String = ""
//var Password: String = ""
@Composable
fun AuthContent(
    loadingState: Boolean,
    isLoading: Boolean,
    onButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
) {
    val emailPassword: AuthViewModel = viewModel()
    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    emailPassword.changeEmail(email.value)
    emailPassword.changePassword(password.value)
//    Email = email.value
//    Password = password.value

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
                Registration(
                   valueSubHeader = stringResource(id = R.string.auth_registration_title),
                    valueHeader = stringResource(id = R.string.auth_registration_subtitle)
                )

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
                EmailMyTextField(
                    email = email.value,
                    onEmailChange  = {email.value = it},
                        labelValue = stringResource(id = R.string.email),
                        painterResource = painterResource(id = R.drawable.envelope_icon)
                )
                Spacer(modifier = Modifier.height(5.dp))
                PasswordMyTextField(
                    password = password.value,
                    onPasswordChange = {password.value = it},
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
                ButtonComp(
                    value = stringResource(id = R.string.register),
                    isLoading = isLoading,
                    onClick = onRegisterButtonClicked
                    //RegisterWithEmailPassword(email.value, password.value)
                )
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

//@Preview
//@Composable
//fun AuthContPreview() {
//    AuthContent(loadingState = false) {
//
//    }
//}

