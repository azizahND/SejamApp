package com.example.sejam.ui.home

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.sejam.R
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.response.DataItemRooms
import com.example.sejam.ui.componen.BottomNavBar
import com.example.sejam.ui.theme.Primary


@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Available Rooms") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomNavBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            // Welcome Message
            item {
                Text(
                    text = "Welcome to Room Booking!",
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = "Find and book your perfect study space",
                    style = MaterialTheme.typography.subtitle1,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Download Guidelines Button
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFF5F5F5)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                viewModel.download()
                                Toast.makeText(context, "Downloading guidelines...", Toast.LENGTH_SHORT).show()
                            }
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = "Guidelines",
                                tint = Primary,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(
                                    text = "Panduan Peminjaman",
                                    style = MaterialTheme.typography.subtitle1.copy(
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = "Download PDF (1.2 MB)",
                                    style = MaterialTheme.typography.caption,
                                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Default.Download,
                            contentDescription = "Download",
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }



            // Room List
            when {
                state.isLoading -> {
                    item { LoadingScreen() }
                }
                state.error != null -> {
                    item {
                        ErrorScreen(
                            error = state.error!!,
                            onRetry = { viewModel.retryLoading() }
                        )
                    }
                }
                state.rooms.isEmpty() -> {
                    item { EmptyBookingsScreen() }
                }
                else -> {
                    items(state.rooms) { room ->
                        RoomItem(
                            room = room,
                            onClick = { roomId ->
                                navController.navigate("room_detail/$roomId")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun StatCard(
    icon: ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Primary.copy(alpha = 0.1f)
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Primary
            )
            Text(
                text = label,
                style = MaterialTheme.typography.caption,
                color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RoomItem(room: DataItemRooms, onClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick(room.id.toString()) },
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Mengubah background menjadi putih
        )
    ) {
        Column {
            // Image and Status Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(room.image),
                    contentDescription = "Room Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )

                // Gradient overlay for better text visibility
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
            }

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                // Room Name and Rating Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = room.name,
                        style = MaterialTheme.typography.h6.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )

                    if (room.averageRating != "0.0000") {
                        Surface(
                            color = Color(0xFFFFF6E5),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFB300),
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "${room.averageRating.toDouble().format(1)}",
                                    style = MaterialTheme.typography.caption,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFFFB300)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = room.description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Capacity and Reviews
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Primary.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Capacity",
                                tint = Primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${room.capacity}",
                                style = MaterialTheme.typography.caption,
                                color = Primary,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (room.totalReviews > 0) {
                        Text(
                            text = "${room.totalReviews} reviews",
                            style = MaterialTheme.typography.caption.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }

                if (!room.facilities.isNullOrEmpty()) {
                    Spacer(modifier = Modifier.height(12.dp))
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        room.facilities.split(",").forEach { facility ->
                            FacilityChip(facility.trim())
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FacilityChip(facility: String) {
    Surface(
        color = Color(0xFFF5F5F5),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp)
        ) {
            // You can add facility-specific icons here
            Text(
                text = facility,
                style = MaterialTheme.typography.caption,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = error,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyBookingsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Rooms Available",
            style = MaterialTheme.typography.h6
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Check back later for available rooms",
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
        )
    }
}



@Composable
private fun BookingItem(
    rooms: DataItemRooms,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rooms.name,
                    style = MaterialTheme.typography.h6,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            if (!rooms.description.isNullOrEmpty()) {
                Text(
                    text = rooms.description,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Capacity",
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "Capacity: ${rooms.capacity}",
                    style = MaterialTheme.typography.body2
                )

                if (rooms.averageRating != "0.0000") {
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFB300)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${rooms.averageRating.toDouble().format(1)} (${rooms.totalReviews})",
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            if (!rooms.facilities.isNullOrEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = rooms.facilities,
                    style = MaterialTheme.typography.body2,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}



private fun Double.format(digits: Int) = "%.${digits}f".format(this)

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}