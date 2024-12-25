package com.example.sejam.ui.peminjaman

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sejam.R
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.response.Bookings
import com.example.sejam.ui.theme.Primary
import java.time.*
import java.time.format.DateTimeFormatter

@Composable
fun PeminjamanDetailScreen(
    bookingId: String?,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PeminjamanViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val detail by viewModel.detail.collectAsState()
    val isEditing by viewModel.isEditing.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(bookingId) {
        viewModel.loadBookingDetail(bookingId.toString())
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Booking Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    if (!isEditing) {
                        IconButton(onClick = { viewModel.toggleEdit() }) {
                            Icon(Icons.Default.Edit, "Edit")
                        }
                    }
                },
                backgroundColor = Primary,
                contentColor = MaterialTheme.colors.onPrimary
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Primary
                    )
                }
                error != null -> {
                    Text(
                        text = error ?: "Unknown error occurred",
                        color = MaterialTheme.colors.error,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp)
                    )
                }
                else -> {
                    detail?.let { booking ->
                        if (isEditing) {
                            EditBookingContent(
                                booking = booking,
                                onSave = { startTime, endTime, purpose ->
                                    viewModel.updateBooking(bookingId.toString(), startTime, endTime, purpose)
                                },
                                onCancel = { viewModel.toggleEdit() }
                            )
                        } else {
                            BookingDetailContent(booking = booking)
                        }
                    }
                }
            }
        }
    }
}





@Composable
private fun EditBookingContent(
    booking: Bookings,
    onSave: (String, String, String) -> Unit,
    onCancel: () -> Unit
) {
    var startDate by remember { mutableStateOf(booking.startTime) }
    var endDate by remember { mutableStateOf(booking.endTime) }
    var purpose by remember { mutableStateOf(booking.purpose) }
    var startDateError by remember { mutableStateOf<String?>(null) }
    var endDateError by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = 4.dp,
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Edit Booking",
                        style = MaterialTheme.typography.h6,
                        fontWeight = FontWeight.Bold,
                        color = Primary
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Start Date Field
                    OutlinedTextField(
                        value = formatDate(startDate),
                        onValueChange = { },
                        label = { Text("Start Date") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        leadingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context) { selectedDate ->
                                    startDate = selectedDate.toString()
                                    startDateError = validateDates(parseDate(startDate), parseDate(endDate))
                                }
                            }) {
                                Icon(Icons.Default.DateRange, "Select Start Date")
                            }
                        },
                        isError = startDateError != null
                    )
                    if (startDateError != null) {
                        Text(
                            text = startDateError!!,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // End Date Field
                    OutlinedTextField(
                        value = formatDate(endDate),
                        onValueChange = { },
                        label = { Text("End Date") },
                        modifier = Modifier.fillMaxWidth(),
                        readOnly = true,
                        leadingIcon = {
                            IconButton(onClick = {
                                showDatePicker(context) { selectedDate ->
                                    endDate = selectedDate.toString()
                                    endDateError = validateDates(parseDate(startDate), parseDate(endDate))
                                }
                            }) {
                                Icon(Icons.Default.DateRange, "Select End Date")
                            }
                        },
                        isError = endDateError != null
                    )
                    if (endDateError != null) {
                        Text(
                            text = endDateError!!,
                            color = MaterialTheme.colors.error,
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = purpose.toString(),
                        onValueChange = { purpose = it },
                        label = { Text("Purpose") },
                        modifier = Modifier.fillMaxWidth(),
                        maxLines = 3
                    )


                    Spacer(modifier = Modifier.height(24.dp))

                    // Action Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = onCancel,
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = Primary
                            )
                        ) {
                            Text("Cancel")
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(
                            onClick = {
                                if (startDateError == null && endDateError == null) {
                                    onSave( startDate , endDate , purpose.toString())
                                }
                            },
                            enabled = startDateError == null && endDateError == null,
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Save Changes")
                        }
                    }
                }
            }
        }
    }
}


private fun showDatePicker(
    context: Context,
    onDateSelected: (LocalDate) -> Unit
) {
    val currentDate = LocalDate.now()

    DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateSelected(
                LocalDate.of(year, month + 1, day)
            )
        },
        currentDate.year,
        currentDate.monthValue - 1,
        currentDate.dayOfMonth
    ).show()
}

private fun validateDates(start: LocalDate, end: LocalDate): String? {
    return when {
        start.isAfter(end) -> "Start date must be before end date"
        start.isBefore(LocalDate.now()) -> "Start date cannot be in the past"
        else -> null
    }
}

private fun parseDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString)
}

private fun formatDate(dateString: String): String {
    return try {
        val localDate = LocalDate.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy")
        localDate.format(formatter)
    } catch (e: Exception) {
        "Invalid date"
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun BookingDetailContent(booking: Bookings) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Room Card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp)
            ) {
                Column {
                    // Image and Status Section
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.s2),
                            contentDescription = "Room Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )

                        // Gradient overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(
                                            Color.Transparent,
                                            Color.Black.copy(alpha = 0.3f)
                                        )
                                    )
                                )
                        )

                        // Status chip
                        Box(
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.TopEnd)
                        ) {
                            Surface(
                                color = when (booking.status) {
                                    "APPROVED" -> Color(0xFF4CAF50)
                                    "PENDING" -> Color(0xFFFFA000)
                                    else -> Color(0xFFE57373)
                                },
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = booking.status,
                                    color = Color.White,
                                    style = MaterialTheme.typography.caption.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = booking.room.name,
                            style = MaterialTheme.typography.h6.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = booking.room.description,
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Booking Time Info
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            BookingTimeChip(
                                icon = Icons.Default.Schedule,
                                label = "Start",
                                value = formatDate(booking.startTime)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = Primary,
                                modifier = Modifier.size(20.dp)
                            )
                            BookingTimeChip(
                                icon = Icons.Default.Schedule,
                                label = "End",
                                value = formatDate(booking.endTime)
                            )
                        }

                        if (!booking.room.facilities.isNullOrEmpty()) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Facilities",
                                style = MaterialTheme.typography.subtitle2.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            FlowRow(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                booking.room.facilities.split(",").forEach { facility ->
                                    FacilityChip(facility.trim())
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Room Info
                        Surface(
                            color = Primary.copy(alpha = 0.1f),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Capacity",
                                    tint = Primary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Capacity ${booking.room.capacity} people",
                                    style = MaterialTheme.typography.caption,
                                    color = Primary,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }


                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Purpose",
                                style = MaterialTheme.typography.subtitle2.copy(
                                    fontWeight = FontWeight.Medium
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))


                        Text(
                            text = booking.purpose.toString(),
                            style = MaterialTheme.typography.body2,
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                            )


                    }
                }
            }
        }
    }
}

@Composable
private fun FacilityChip(facility: String) {
    androidx.compose.material3.Surface(
        color = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            // You can add facility-specific icons here
            androidx.compose.material3.Text(
                text = facility,
                style = MaterialTheme.typography.caption,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun BookingTimeChip(
    icon: ImageVector,
    label: String,
    value: String
) {
    Surface(
        color = Primary.copy(alpha = 0.1f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.caption,
                    color = Primary
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.Medium
                    ),
                    color = Primary
                )
            }
        }
    }
}

@Composable
private fun DetailItem(
    icon: ImageVector,
    title: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            androidx.compose.material3.Text(
                text = title,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
            )
            androidx.compose.material3.Text(
                text = value,
                style = MaterialTheme.typography.body1
            )
        }
    }
}
