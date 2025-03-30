package com.example.dsw53518_kotlin.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dsw53518_kotlin.ui.theme.DarkPurple
import com.example.dsw53518_kotlin.ui.theme.Grey


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    leadingIcon: Painter? = null,
    onTrailingIconClick: () -> Unit = {},
    trailingIcon: Painter? = null,
    visualTransformation: VisualTransformation? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {

    val customTextFieldColors = TextFieldDefaults.colors(
        focusedIndicatorColor = DarkPurple,
        unfocusedIndicatorColor = DarkPurple,
        focusedLabelColor = Grey,
        unfocusedLabelColor = Grey,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
    )

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        colors = customTextFieldColors,
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        shape = RoundedCornerShape(12.dp),
        leadingIcon = {
            if (leadingIcon != null)
                Icon(
                    modifier = Modifier.size(20.dp),
                    painter = leadingIcon,
                    contentDescription = null,
                    tint = Color.Unspecified
                )
        },
        trailingIcon = {
            if (trailingIcon != null)
                IconButton(onClick = onTrailingIconClick) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = trailingIcon, contentDescription = null
                    )
                }
        },
        visualTransformation = if(visualTransformation != null) {
            visualTransformation
        } else {
            VisualTransformation.None
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType
        ),

    )
}