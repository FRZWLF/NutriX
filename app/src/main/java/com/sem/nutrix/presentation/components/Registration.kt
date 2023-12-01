package com.sem.nutrix.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sem.nutrix.R

@Composable
fun Registration(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    Surface(
        color = backgroundColor
    ) {
        Column(
//            modifier = Modifier
//                .fillMaxSize()
        ) {
            Text(
                text = stringResource(id = R.string.auth_registration_title),
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(min = 30.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal
                ),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.auth_registration_subtitle),
                modifier = modifier
                    .fillMaxWidth()
                    .heightIn(),
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal
                ),
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center

            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(
    modifier: Modifier = Modifier,
    labelValue: String,
    painterResource: Painter,
    shape: Shape = Shapes().extraSmall,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focusBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val textValue = remember{
        mutableStateOf("")
    }
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = focusBorderColor,
            focusedLabelColor = focusBorderColor,
            cursorColor = focusBorderColor
        ),
        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordMyTextField(
    modifier: Modifier = Modifier,
    labelValue: String,
    painterResource: Painter,
    shape: Shape = Shapes().extraSmall,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focusBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val password = remember{
        mutableStateOf("")
    }

    val passwordVisible = remember{
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = focusBorderColor,
            focusedLabelColor = focusBorderColor,
            cursorColor = focusBorderColor
        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        value = password.value,
        onValueChange = {
            password.value = it
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value) {
                Icons.Filled.Visibility
            } else  {
                Icons.Filled.VisibilityOff
            }
            
            var description = if (passwordVisible.value) {
                stringResource(id = R.string.hide_password)
            } else {
                stringResource(id = R.string.show_password)
            }
            
            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun Checkbox(
    value: String,
    onTextSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val checkedState = remember {
            mutableStateOf(false)
        }
        androidx.compose.material3.Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                checkedState.value = !checkedState.value
            }
        )

        ClickableText(value = value, onTextSelected)
    }
}

@Composable
fun ClickableText(value: String, onTextSelected: (String) -> Unit) {
    val initialText = "By continuing you accept our "
    val privacyPolicyText = "Privacy Policy"
    val andText = " and "
    val termsAndConditionsText = "Term of Use"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = privacyPolicyText, annotation = privacyPolicyText)
            append(privacyPolicyText)
        }
        append(andText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = termsAndConditionsText, annotation = termsAndConditionsText)
            append(termsAndConditionsText)
        }
    }
    androidx.compose.foundation.text.ClickableText(
        text = annotatedString,
        onClick = {offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also {span ->
                    Log.d("ClickableText", "{${span.item}}")
                    if (span.item == termsAndConditionsText || (span.item == privacyPolicyText)){
                        onTextSelected(span.item)
                    }
                }

        }
    )
}

@Composable
fun ButtonComp(
    value:String,
    onClick: () -> Unit
    ) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondary
                        )
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DividerText() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 1.dp
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = stringResource(id = R.string.divider_text),
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.surfaceVariant
            )
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            color = MaterialTheme.colorScheme.surfaceVariant,
            thickness = 1.dp
        )
    }
}

@Composable
fun ClickableLoginText(onTextSelected: (String) -> Unit) {
    val initialText = "Already have an account? "
    val loginText = "Login"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = loginText, annotation = loginText)
            append(loginText)
        }
    }
    androidx.compose.foundation.text.ClickableText(
        text = annotatedString,
        onClick = {offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also {span ->
                    Log.d("ClickableText", "{${span.item}}")
                    if (span.item == loginText){
                        onTextSelected(span.item)
                    }
                }

        }
    )
}



@Composable
@Preview
fun RegistrationPreview() {
    Registration ()
}