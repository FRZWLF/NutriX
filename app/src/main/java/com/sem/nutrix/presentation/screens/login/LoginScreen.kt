package com.sem.nutrix.presentation.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sem.nutrix.R
import com.sem.nutrix.presentation.components.ButtonComp
import com.sem.nutrix.presentation.components.ClickableLoginText
import com.sem.nutrix.presentation.components.ClickableRegistrationText
import com.sem.nutrix.presentation.components.DividerText
import com.sem.nutrix.presentation.components.GoogleButton
import com.sem.nutrix.presentation.components.MyTextField
import com.sem.nutrix.presentation.components.PasswordMyTextField
import com.sem.nutrix.presentation.components.Registration


@Composable
fun LoginScreen(
    loadingState: Boolean,
    onButtonClicked: () -> Unit,
    onRegisterButtonClicked: () -> Unit,
){
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
                    labelValue = stringResource(id = R.string.email),
                    painterResource = painterResource(id = R.drawable.envelope_icon)
                )
                Spacer(modifier = Modifier.height(5.dp))
                PasswordMyTextField(
                    labelValue = stringResource(id = R.string.password),
                    painterResource = painterResource(id = R.drawable.ic_lock)
                )
            }
            Column(
                modifier = Modifier.weight(weight = 10f),
                verticalArrangement = Arrangement.Bottom,


            ) {

                Spacer(modifier = Modifier.height(20.dp))
                ButtonComp(
                    value = stringResource(id = R.string.login),
                    onClick = onRegisterButtonClicked,
//                    RegisterWithEmailPassword()
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

            ClickableRegistrationText(
                onTextSelected = {
                    //Router zu Login-Screen
                }
            )


        }



    }
}





@Preview
@Composable
fun LoginScreenPreview(){
    //LoginScreen()
}
