package com.example.medicationreminder.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Eye
import compose.icons.feathericons.EyeOff

@Composable
fun FormBasicComponent(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    onError: Boolean,
    enabled: Boolean = true
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall,
            enabled = enabled,
            singleLine = singleLine,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.TopStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = if (onError) "$title is required" else hintText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (onError) Color.Red else MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun FormClickedComponent(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    icons: ImageVector,
    value: String,
    onValueChange: (String) -> Unit,
    onError: Boolean = false,
    enabled: Boolean = false,
    onIconClick: () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .clickable { onIconClick() }
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)),
            value = value,
            enabled = enabled,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = hintText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                    innerTextField()
                    Icon(
                        imageVector = icons,
                        contentDescription = title,
                    )
                }
            }
        )
    }
}

@Composable
fun FormEmailComponent(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    value: String,
    onValueChange: (String) -> Unit,
    onError: Boolean
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = if (onError) "$title is required" else hintText,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = if (onError) Color.Red else MaterialTheme.colorScheme.tertiary
                            )
                        )
                    }
                    innerTextField()
                }
            }
        )
    }
}

@Composable
fun FormPasswordComponent(
    modifier: Modifier = Modifier,
    title: String,
    hintText: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onError: Boolean,
    onIconClick: () -> Unit
) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        BasicTextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
                .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(12.dp)),
            value = value,
            onValueChange = {
                onValueChange(it)
            },
            textStyle = MaterialTheme.typography.bodySmall,
            singleLine = true,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier.weight(1f)
                        ) {
                            if (value.isEmpty()) {
                                Text(
                                    text = if (onError) "$title is required" else hintText,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = if (onError) Color.Red else MaterialTheme.colorScheme.tertiary
                                    )
                                )
                            }
                            innerTextField()
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = if (isPasswordVisible) FeatherIcons.Eye else FeatherIcons.EyeOff,
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                            modifier = Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    onIconClick()
                                }
                        )
                    }
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormBasicPreview() {
    MedicationReminderTheme {
        FormBasicComponent(
            title = "Name",
            hintText = "Masukan nama",
            value = "test",
            onValueChange = {},
            onError = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormClickedPreview() {
    MedicationReminderTheme {
        FormClickedComponent(
            title = "click",
            icons = Icons.Outlined.KeyboardArrowDown,
            hintText = "",
            value = "",
            onValueChange = {},
            onError = false,
            enabled = false,
            onIconClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormEmailPreview() {
    MedicationReminderTheme {
        FormEmailComponent(
            title = "Email",
            hintText = "Masukan alamat email",
            value = "test",
            onValueChange = {},
            onError = false,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FormPasswordPreview() {
    MedicationReminderTheme {
        FormPasswordComponent(
            title = "Password",
            hintText = "",
            value = "",
            onValueChange = {},
            isPasswordVisible = false,
            onError = false,
            onIconClick = {}
        )
    }
}