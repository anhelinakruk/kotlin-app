package com.example.dsw53518_kotlin.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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

@Composable
fun LoginPage(navController: NavController) {
    val textStyle = TextStyle(
        color = DarkPurple
    )

    val socialIcons = listOf(
        R.drawable.google_icon to "Google icon",
        R.drawable.facebook_icon to "FB icon",
        R.drawable.x_icon to "X icon",
        R.drawable.linkedin_icon to "LinkedIn icon"
    )


    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var hidePassword by remember { mutableStateOf(true) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Top
    ){
        Image(
            painterResource(R.drawable.logo),
            contentDescription = "logo",
            modifier = Modifier
                .padding(top = 100.dp, bottom = 40.dp)
                .size(129.dp)
                .align(Alignment.CenterHorizontally)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text("Sign in",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = DarkPurple,
            )
            CustomTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier,
                label = "Email or User Name",
                leadingIcon = painterResource(R.drawable.person_icon),
                keyboardType = KeyboardType.Email
            )
            CustomPasswordField(
                password = password,
                onPasswordChange = { password = it },
                onTrailingIconClick = { hidePassword = !hidePassword },
                hidePassword = hidePassword
            )
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton ({}) {
                    Text("Forget Password?", style = textStyle, fontWeight = FontWeight.Bold)
                }
            }
            Button(
                onClick = {navController.navigate(Routes.registerPage)},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                contentColor = Color.White,
                containerColor = Pink,
                disabledContentColor = Color.LightGray,
                disabledContainerColor = Color.Gray
                )
            ) {
                Text(text = "Sign in")
            }
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth().padding(top = 50.dp)
        ) {
            Text(text = "Or Sign in With", style = textStyle)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
        ) {
            socialIcons.forEach { (iconRes, contentDesc) ->
                ElevatedButton (
                    onClick = {},
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    contentPadding = PaddingValues(10.dp),
                ) {
                    Image(
                        painter = painterResource(iconRes),
                        contentDescription = contentDesc,
                        modifier = Modifier.size(40.dp),
                    )
                }
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
            modifier = Modifier.fillMaxWidth().padding(top = 120.dp)
        ) {
            Text("Don't have account?", style = textStyle)
            TextButton({
                navController.navigate(Routes.registerPage)
            }) {
                Text("Sign Up", style = textStyle, fontWeight = FontWeight.Bold

                )
            }
        }
    }
}