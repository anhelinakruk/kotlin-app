package com.example.dsw53518_kotlin.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.dsw53518_kotlin.R

@Composable
fun CustomPasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    onTrailingIconClick: () -> Unit,
    hidePassword: Boolean,
    modifier: Modifier = Modifier
) {
    val trailingIcon =
        if (hidePassword) painterResource(R.drawable.eye)
        else painterResource(R.drawable.eye_icon)

    val visualTransformation =
        if (hidePassword) PasswordVisualTransformation()
        else VisualTransformation.None

    CustomTextField(
        modifier = modifier.fillMaxWidth(),
        value = password,
        onValueChange = onPasswordChange,
        leadingIcon = painterResource(R.drawable.lock_icon),
        trailingIcon = trailingIcon,
        onTrailingIconClick = onTrailingIconClick,
        label = "Password",
        visualTransformation = visualTransformation,
        keyboardType = KeyboardType.Password
    )
}
