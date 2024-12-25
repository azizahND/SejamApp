package com.example.sejam.ui.peminjaman

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.response.MyBookingsItem
import com.example.sejam.ui.componen.BottomNavBar
import com.example.sejam.ui.home.HomeViewModel
import com.example.sejam.ui.theme.Primary
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun PeminjamanScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: PeminjamanViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(16.dp)
        ) {
            Text(
                text = "My Bookings",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }

        // Booking List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(state) { booking ->
                BookingCard(booking = booking, navController = navController)
            }
        }

        // Bottom Navigation
        BottomNavBar(navController = navController)
    }
}

@Composable
private fun BookingCard(booking: MyBookingsItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("bookingDetail/${booking.id}")
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Room Name and Status
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = booking.room.name,
                    style = MaterialTheme.typography.h6,
                    color = Primary,
                    fontWeight = FontWeight.Bold
                )
                StatusChip(status = booking.status)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Booking Details
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Date and Time
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    InfoItem(
                        icon = Icons.Default.DateRange,
                        label = "Start",
                        value = formatDate(booking.startTime)
                    )
                    InfoItem(
                        icon = Icons.Default.DateRange,
                        label = "End",
                        value = formatDate(booking.endTime)
                    )
                }

                Divider(modifier = Modifier.padding(vertical = 8.dp))

                // Room Details
                Text(
                    text = "Room Details",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = booking.room.description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                )

                // Facilities
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = booking.room.facilities,
                        style = MaterialTheme.typography.caption
                    )
                }

                // Capacity
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = Primary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Capacity: ${booking.room.capacity} people",
                        style = MaterialTheme.typography.caption
                    )
                }

                if (booking.purpose != null) {
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    Text(
                        text = "Purpose: ${booking.purpose}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val backgroundColor = when (status.toLowerCase()) {
        "pending" -> Color(0xFFFFF176)
        "approved" -> Color(0xFF81C784)
        "rejected" -> Color(0xFFE57373)
        else -> Color.Gray
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = status.capitalize(),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            style = MaterialTheme.typography.caption,
            color = Color.Black
        )
    }
}

@Composable
private fun InfoItem(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.caption
            )
        }
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium
        )
    }
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
