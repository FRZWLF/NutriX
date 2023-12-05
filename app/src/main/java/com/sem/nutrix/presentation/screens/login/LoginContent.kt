package com.sem.nutrix.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.sem.nutrix.presentation.components.ClickableRegistrationText
import com.sem.nutrix.presentation.components.DividerText
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.components.LoginButtonComp
import com.sem.nutrix.presentation.components.MyTextField
import com.sem.nutrix.presentation.components.PasswordMyTextField
import com.sem.nutrix.presentation.components.Registration
import com.sem.nutrix.presentation.screens.auth.AuthViewModel


@Composable
fun LoginContent(
    loadingState: Boolean,
    isLoading: Boolean,
    onButtonClicked: () -> Unit,
    onLoginButtonClicked: () -> Unit,
    toRegistrationClicked: () -> Unit
){
    val emailPassword: AuthViewModel = viewModel()
    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    emailPassword.changeEmail(email.value)
    emailPassword.changePassword(password.value)

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
                    valueSubHeader = stringResource(id = R.string.auth_login_title),
                    valueHeader = stringResource(id = R.string.auth_login_subtitle)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Column(
                //modifier = Modifier.weight(weight = 2f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(5.dp))
                MyTextField(
                    value = email.value,
                    onValueChange  = {email.value = it},
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
            }
            Column(
                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Bottom,


                ) {

                Spacer(modifier = Modifier.height(20.dp))
                LoginButtonComp(
                    value = stringResource(id = R.string.login),
                    email = email.value,
                    password = password.value,
                    isLoading = isLoading,
                    onClick = onLoginButtonClicked
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
                    primaryText = stringResource(id = R.string.google_sign_in)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            ClickableRegistrationText(
                onTextSelected = toRegistrationClicked
            )


        }



    }
}





//@Preview
//@Composable
//fun LoginContentPreview(){

    //LoginContent()
//}
