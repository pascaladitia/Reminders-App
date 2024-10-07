package com.example.medicationreminder.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.medicationreminder.utils.AppConstants.Companion.FORMAT_DATE
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerBottomSheet(
    totalValue: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val coroutineScope = rememberCoroutineScope()
    val dateRangePickerState = rememberDateRangePickerState()
    val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var startDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }
    var endDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        containerColor = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(16.dp),
        content = {
            DateRangePicker(
                modifier = Modifier
                    .padding(bottom = 32.dp)
                    .height(height = 450.dp),
                state = dateRangePickerState,
               /* dateValidator = { timestamp ->
                    timestamp > System.currentTimeMillis()
                }, */
                title = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Select Date Leave",
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                },
                headline = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(end = 24.dp)
                        ) {
                            TextButton(onClick = {
                                coroutineScope.launch {
                                    modalBottomSheetState.hide()
                                    onDismiss()
                                }
                            }) {
                                Text(text = "Cancel")
                            }
                            TextButton(onClick = {
                                if (dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null) {
                                    startDate =
                                        dateRangePickerState.selectedStartDateMillis ?: startDate
                                    endDate = dateRangePickerState.selectedEndDateMillis ?: endDate

                                    val formatter = SimpleDateFormat(FORMAT_DATE, Locale.ROOT)
                                    val totalMillis = endDate - startDate
                                    val totalDays = TimeUnit.MILLISECONDS.toDays(totalMillis)
                                    totalValue("$totalDays")

                                    onConfirm(
                                        formatter.format(Date(startDate)),
                                        formatter.format(Date(endDate))
                                    )
                                    coroutineScope.launch {
                                        modalBottomSheetState.hide()
                                        onDismiss()
                                    }
                                }
                            }) {
                                Text(text = "Confirm")
                            }
                        }
                    }
                },
                showModeToggle = false,
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePickerDialog(
    totalValue: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: (String, String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val dateRangePickerState = rememberDateRangePickerState()
    var startDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }
    var endDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }

        DatePickerDialog(
            onDismissRequest = {
                onDismiss()
            },
            shape = RoundedCornerShape(16.dp),
            confirmButton = {
                TextButton(onClick = {
                    if (dateRangePickerState.selectedStartDateMillis != null && dateRangePickerState.selectedEndDateMillis != null) {
                        onDismiss()
                        startDate = dateRangePickerState.selectedStartDateMillis ?: startDate
                        endDate = dateRangePickerState.selectedEndDateMillis ?: endDate

                        val formatter = SimpleDateFormat(FORMAT_DATE, Locale.ROOT)
                        onConfirm(formatter.format(Date(startDate)), formatter.format(Date(endDate)))

                        val totalMillis = endDate - startDate
                        val totalDays = TimeUnit.MILLISECONDS.toDays(totalMillis)
                        totalValue("$totalDays")
                    }
                }) {
                    Text(text = "Confirm")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            dismissButton = {
                TextButton(onClick = {
                    onDismiss()
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                title = {},
                headline = {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Select Date Leave",
                        textAlign = TextAlign.Center
                    )
                },
                showModeToggle = false,
                modifier = Modifier.height(height = 500.dp)
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit
) {
    val calendar = Calendar.getInstance()
    val dateRangePickerState = rememberDatePickerState()
    var startDate by remember {
        mutableLongStateOf(calendar.timeInMillis)
    }

    DatePickerDialog(
        onDismissRequest = {
            onDismiss()
        },
        shape = RoundedCornerShape(16.dp),
        confirmButton = {
            TextButton(onClick = {
                if (dateRangePickerState.selectedDateMillis != null) {
                    onDismiss()
                    startDate = dateRangePickerState.selectedDateMillis ?: startDate

                    val formatter = SimpleDateFormat(FORMAT_DATE, Locale.ROOT)
                    onConfirm(formatter.format(Date(startDate)))
                }
            }) {
                Text(text = "Confirm")
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = dateRangePickerState,
            title = {},
            headline = {
                Text(
                    modifier = Modifier.padding(start = 32.dp, bottom = 8.dp),
                    text = "Select Date",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleMedium
                )
            },
        )
    }
}

