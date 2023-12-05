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
import com.sem.nutrix.presentation.components.Checkbox
import com.sem.nutrix.presentation.components.ClickableLoginText
import com.sem.nutrix.presentation.components.DividerText
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.components.MyTextField
import com.sem.nutrix.presentation.components.PasswordMyTextField
import com.sem.nutrix.presentation.components.Registration
import com.sem.nutrix.presentation.components.RegistrationButtonComp

@Composable
fun AuthContent(
    loadingState: Boolean,
    isLoading: Boolean,
    onButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
    toLoginClicked: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel()
    val email = remember{
        mutableStateOf("")
    }
    val password = remember{
        mutableStateOf("")
    }
    val firstName = remember{
        mutableStateOf("")
    }
    val lastName = remember{
        mutableStateOf("")
    }
    val checkedState = remember {
        mutableStateOf(false)
    }

    viewModel.changeEmail(email.value)
    viewModel.changePassword(password.value)
    viewModel.changeFirstName(firstName.value)
    viewModel.changeLastName(lastName.value)


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
                    value = firstName.value,
                    onValueChange = {firstName.value = it},
                    labelValue = stringResource(id = R.string.first_name),
                    painterResource = painterResource(id = R.drawable.user_profile)
                )
                Spacer(modifier = Modifier.height(5.dp))
                MyTextField(
                    value = lastName.value,
                    onValueChange = {lastName.value = it},
                    labelValue = stringResource(id = R.string.last_name),
                    painterResource = painterResource(id = R.drawable.user_profile)
                )
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
                Checkbox(
                    value = stringResource(id = R.string.terms_and_conditions),
                    checkedState = checkedState,
                    onCheckedStateChanged = { checkedState.value = it }
                ) {

                }

                Spacer(modifier = Modifier.height(20.dp))
                RegistrationButtonComp(
                    value = stringResource(id = R.string.register),
                    firstName = firstName.value,
                    lastName = lastName.value,
                    email = email.value,
                    password = password.value,
                    checked = checkedState.value,
                    isLoading = isLoading,
                    onClick = onRegisterButtonClicked
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
                onTextSelected = toLoginClicked
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

