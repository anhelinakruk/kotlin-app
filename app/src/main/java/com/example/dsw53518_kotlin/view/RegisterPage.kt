package com.example.dsw53518_kotlin.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.dsw53518_kotlin.R
import com.example.dsw53518_kotlin.ui.theme.DarkPurple
import com.example.dsw53518_kotlin.ui.theme.Pink
import com.example.dsw53518_kotlin.utils.CustomPasswordField
import com.example.dsw53518_kotlin.utils.CustomTextField
import com.example.dsw53518_kotlin.utils.Routes
import com.example.dsw53518_kotlin.viewmodel.AuthState
import com.example.dsw53518_kotlin.viewmodel.AuthViewModel

@Composable
fun RegisterPage(
    navController: NavController,
    authViewModel: AuthViewModel,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmationPassword by remember { mutableStateOf("") }
    var hidePassword by remember { mutableStateOf(true) }
    var hideConfirmationPassword by remember { mutableStateOf(true) }

    var nameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmationPasswordError by remember { mutableStateOf<String?>(null) }

    var nameHasFocus by remember { mutableStateOf(false) }
    var emailHasFocus by remember { mutableStateOf(false) }
    var passwordHasFocus by remember { mutableStateOf(false) }
    var confirmationPasswordHasFocus by remember { mutableStateOf(false) }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate(Routes.homePage)
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_LONG).show()
            else -> Unit
        }
    }

    fun validateName() {
        nameError = when {
            name.length < 3 -> "Name must be at least 3 characters"
            !name.all { it.isLetter() || it.isWhitespace() } -> "Name can contain only letters"
            else -> null
        }
    }

    fun validateEmail() {
        emailError = if (!email.contains("@") || !email.contains(".com")) "Invalid email address" else null
    }

    fun validatePassword() {
        passwordError = if (password.length < 8) "Password must be at least 8 characters" else null
    }

    fun validateConfirmationPassword() {
        confirmationPasswordError = if (password != confirmationPassword) "Passwords do not match" else null
    }

    fun validateAll(): Boolean {
        validateName()
        validateEmail()
        validatePassword()
        validateConfirmationPassword()
        return listOf(nameError, emailError, passwordError, confirmationPasswordError).all { it == null }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { navController.navigate(Routes.loginPage) },
                modifier = Modifier.padding(top = 70.dp),
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(R.drawable.back_icon),
                    contentDescription = null
                )
                Text(
                    "Back",
                    color = DarkPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                )
            }
            Image(
                painterResource(R.drawable.ellipse),
                contentDescription = "Corner image",
                modifier = Modifier.size(width = 150.dp, height = 118.dp),
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp),
        ) {
            Text(
                "Sing up",
                color = DarkPurple,
                fontSize = 30.sp,
                fontWeight = FontWeight.W700,
                modifier = Modifier.padding(top = 50.dp)
            )

            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Full Name",
                    leadingIcon = painterResource(R.drawable.person_icon),
                    keyboardType = KeyboardType.Text,
                    modifier = Modifier.onFocusChanged {
                        if (nameHasFocus && !it.isFocused) {
                            validateName()
                        }
                        nameHasFocus = it.isFocused
                    }
                )
                if (nameError != null) {
                    Text(
                        text = nameError!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                CustomTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    leadingIcon = painterResource(R.drawable.email_icon),
                    keyboardType = KeyboardType.Email,
                    modifier = Modifier.onFocusChanged {
                        if (emailHasFocus && !it.isFocused) {
                            validateEmail()
                        }
                        emailHasFocus = it.isFocused
                    }
                )
                if (emailError != null) {
                    Text(
                        text = emailError!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                CustomPasswordField(
                    password = password,
                    onPasswordChange = { password = it },
                    onTrailingIconClick = { hidePassword = !hidePassword },
                    hidePassword = hidePassword,
                    modifier = Modifier.onFocusChanged {
                        if (passwordHasFocus && !it.isFocused) {
                            validatePassword()
                        }
                        passwordHasFocus = it.isFocused
                    }
                )
                if (passwordError != null) {
                    Text(
                        text = passwordError!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                CustomPasswordField(
                    password = confirmationPassword,
                    onPasswordChange = { confirmationPassword = it },
                    onTrailingIconClick = { hideConfirmationPassword = !hideConfirmationPassword },
                    hidePassword = hideConfirmationPassword,
                    modifier = Modifier.onFocusChanged {
                        if (confirmationPasswordHasFocus && !it.isFocused) {
                            validateConfirmationPassword()
                        }
                        confirmationPasswordHasFocus = it.isFocused
                    }
                )
                if (confirmationPasswordError != null) {
                    Text(
                        text = confirmationPasswordError!!,
                        color = Color.Red,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 8.dp, top = 2.dp, bottom = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    if (validateAll()) {
                        authViewModel.signup(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Pink,
                    disabledContentColor = Color.LightGray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Sing up", fontSize = 18.sp, fontWeight = FontWeight.W700)
            }

            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 64.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(
                    "Already have an account ? ",
                    color = DarkPurple,
                    fontSize = 18.sp,
                )
                Text(
                    "Sing In",
                    color = DarkPurple,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.clickable { navController.navigate(Routes.loginPage) }
                )
            }
        }
    }
}
