package com.sem.nutrix.presentation.components

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sem.nutrix.R
import kotlinx.coroutines.launch

@Composable
fun Registration(
    valueSubHeader: String,
    valueHeader: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
) {
    Surface(
        color = backgroundColor
    ) {
        Column(
        ) {
            Text(
                text = valueSubHeader,
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
                text = valueHeader,
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
    value: String,
    onValueChange: (String) -> Unit,
    painterResource: Painter,
    shape: Shape = Shapes().extraSmall,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focusBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val scope = rememberCoroutineScope()
    val focuseManager = LocalFocusManager.current

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
        value = value,
        onValueChange = {
            onValueChange(it)
        },
        leadingIcon = {
            Icon(painter = painterResource, contentDescription = "")
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = {
                scope.launch {
                    focuseManager.moveFocus(FocusDirection.Down)
                }
            }
        ),
        maxLines = 1,
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordMyTextField(
    modifier: Modifier = Modifier,
    labelValue: String,
    password: String,
    onPasswordChange: (String) -> Unit,
    painterResource: Painter,
    shape: Shape = Shapes().extraSmall,
    borderColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    focusBorderColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val scope = rememberCoroutineScope()
    val focuseManager = LocalFocusManager.current
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
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Next
        ),
        value = password,
        onValueChange = {
           onPasswordChange(it)
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
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardActions = KeyboardActions(
            onNext = {
                scope.launch {
                    focuseManager.moveFocus(FocusDirection.Down)
                }
            }
        ),
        maxLines = 1,
        singleLine = true
    )
}

@Composable
fun Checkbox(
    value: String,
    checkedState: MutableState<Boolean>,
    onCheckedStateChanged: (Boolean) -> Unit,
    onTextSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        androidx.compose.material3.Checkbox(
            checked = checkedState.value,
            onCheckedChange = {
                onCheckedStateChanged(!checkedState.value)
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
fun RegistrationButtonComp(
    modifier: Modifier = Modifier,
    value:String,
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    checked: Boolean,
    isLoading: Boolean = false,
    secondaryText: String = stringResource(id = R.string.google_sec_text),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
    ) {
    val context = LocalContext.current
    var buttonText by remember { mutableStateOf(value) }

    LaunchedEffect(key1 = isLoading) {
        buttonText = if (isLoading) secondaryText else value
    }

    Surface(
        modifier = modifier
            .clickable(enabled = !isLoading) {
                if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && checked) {
                    onClick()
                } else if (email.isNotEmpty() && password.isNotEmpty() && firstName.isNotEmpty() && lastName.isNotEmpty() && !checked) {
                    Toast.makeText(
                        context,
                        "Accept our Term of Use.",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        context,
                        "Fields cannot be empty.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                                             },
        color = backgroundColor
    ){
        Row(
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
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 1200,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = buttonText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
        }
    }
}

@Composable
fun LoginButtonComp(
    modifier: Modifier = Modifier,
    value:String,
    email: String,
    password: String,
    isLoading: Boolean = false,
    secondaryText: String = stringResource(id = R.string.google_sec_text),
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    progressIndicatorColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var buttonText by remember { mutableStateOf(value) }

    LaunchedEffect(key1 = isLoading) {
        buttonText = if (isLoading) secondaryText else value
    }

    Surface(
        modifier = modifier
            .clickable(enabled = !isLoading) {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    onClick()
                } else {
                    Toast.makeText(
                        context,
                        "Fields cannot be empty.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            },
        color = backgroundColor
    ){
        Row(
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
                )
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 600,
                        easing = LinearOutSlowInEasing
                    )
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            Text(
                text = buttonText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            if (isLoading) {
                Spacer(modifier = Modifier.width(16.dp))
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(16.dp),
                    strokeWidth = 2.dp,
                    color = progressIndicatorColor
                )
            }
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
fun ClickableLoginText(onTextSelected: () -> Unit) {
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
                        onTextSelected()//span.item
                    }
                }

        }
    )
}

@Composable
fun ClickableRegistrationText(onTextSelected: () -> Unit) {
    val initialText = "Don`t have an account yet? "
    val registrationText = "Registration"
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)){
            pushStringAnnotation(tag = registrationText, annotation = registrationText)
            append(registrationText)
        }
    }
    androidx.compose.foundation.text.ClickableText(
        text = annotatedString,
        onClick = {offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.also {span ->
                    Log.d("ClickableText", "{${span.item}}")
                    if (span.item == registrationText){
                        onTextSelected()
                    }
                }

        }
    )
}

@Composable
@Preview
fun RegistrationPreview() {
    //Registration()
}