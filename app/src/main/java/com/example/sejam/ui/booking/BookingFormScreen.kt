package com.example.sejam.ui.booking

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sejam.ViewModelFactory
import com.example.sejam.ui.home.HomeViewModel
import com.example.sejam.ui.theme.Primary
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingFormScreen(
    roomId: String,
    navController: NavController,
    viewModel: BookingViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Room") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        BookingFormContent(
            state = state,
            onStartTimeClick = { viewModel.showStartDatePicker(context) },
            onEndTimeClick = { viewModel.showEndDatePicker(context) },
            onPurposeChange = { viewModel.updatePurpose(it) },
            onSubmit = { viewModel.createBooking(roomId) },
            modifier = Modifier.padding(paddingValues)
        )
    }

    // Show loading dialog
    if (state.isLoading) {
        AlertDialog(
            onDismissRequest = { },
            text = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator()
                    Text("Creating booking...")
                }
            },
            confirmButton = { }
        )
    }



    // Show success dialog
    if (state.bookingCreated) {
        AlertDialog(
            onDismissRequest = {
                viewModel.resetState()
                navController.navigateUp()
            },
            title = { Text("Success") },
            text = { Text("Booking created successfully") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.resetState()
                    navController.navigateUp()
                }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
private fun BookingFormContent(
    state: BookingFormState,
    onStartTimeClick: () -> Unit,
    onEndTimeClick: () -> Unit,
    onPurposeChange: (String) -> Unit,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Start Date Field
        OutlinedCard(
            onClick = onStartTimeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.DateRange, "Start Date")
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Start Date",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = state.startTime?.format(
                            DateTimeFormatter.ofPattern("dd MMM yyyy")
                        ) ?: "Select start date",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // End Date Field
        OutlinedCard(
            onClick = onEndTimeClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Default.DateRange, "End Date")
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        text = "End Date",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = state.endTime?.format(
                            DateTimeFormatter.ofPattern("dd MMM yyyy")
                        ) ?: "Select end date",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

        // Purpose Field
        OutlinedTextField(
            value = state.purpose,
            onValueChange = onPurposeChange,
            label = { Text("Purpose") },
            modifier = Modifier.fillMaxWidth(),
            minLines = 3,
            maxLines = 5
        )

        // Submit Button
        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isFormValid()
        ) {
            Icon(Icons.Default.Check, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text("Submit Booking")
        }
    }
}