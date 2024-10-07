package com.example.medicationreminder.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.medicationreminder.ui.theme.MedicationReminderTheme
import compose.icons.FeatherIcons
import compose.icons.feathericons.Check

@Composable
fun ShowSuccessDialog(
    isDialogVisible: Boolean? = false,
    onClose: () -> Unit
) {
    if (isDialogVisible == true) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true
            ),
            title = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = FeatherIcons.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.1f),
                                RoundedCornerShape(20.dp)
                            )
                            .padding(16.dp),
                        tint = MaterialTheme.colorScheme.onSecondary
                    )
                }
            },
            text = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Success",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.primary
                        ),
                    )
                }
            },
            onDismissRequest = { onClose() },
            confirmButton = {},
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.background,
            dismissButton = {}
        )
    }
}

@Preview
@Composable
fun DialogSuccessPreview() {
    MedicationReminderTheme {
        ShowSuccessDialog(
            isDialogVisible = true
        ) {
            
        }
    }
}