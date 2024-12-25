package com.example.sejam.ui.room

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.response.BookingsItem
import com.example.sejam.data.response.DataItemRooms
import com.example.sejam.data.response.DetailRoomsData
import com.example.sejam.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomDetailScreen(
    roomId: String,
    navController: NavController,
    viewModel: RoomDetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    )
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(roomId) {
        viewModel.loadRoomDetail(roomId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Room Detail") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Primary,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        when {
            state.isLoading -> LoadingScreen()
            state.error != null -> ErrorScreen(state.error!!) { viewModel.retryLoading() }
            state.room != null -> RoomDetailContent(
                navController = navController,
                room = state.room!!,
                onBookClick = {
                    navController.navigate("booking_form/${state.room!!.id}")
                },
                modifier = Modifier.padding(paddingValues)
            )
        }
    }
}

@Composable
fun LoadingScreen() {

    androidx.compose.material.CircularProgressIndicator(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .wrapContentSize(Alignment.Center)

    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun RoomDetailContent(
    navController: NavController,
    room: DetailRoomsData,
    onBookClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column {
                    // Image Section with Gradient Overlay
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                    ) {
                        AsyncImage(
                            model = room.image,
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
                    }

                    // Content Section
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
                                style = androidx.compose.material.MaterialTheme.typography.h6.copy(
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
                                            style = androidx.compose.material.MaterialTheme.typography.caption,
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
                            text = room.description ?: "No description available",
                            style = androidx.compose.material.MaterialTheme.typography.body2,
                            color = androidx.compose.material.MaterialTheme.colors.onSurface.copy(alpha = 0.7f),
                            lineHeight = 20.sp
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Capacity and Reviews Row
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
                                        style = androidx.compose.material.MaterialTheme.typography.caption,
                                        color = Primary,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }

                            Text(
                                text = "${room.totalReviews} reviews",
                                style = androidx.compose.material.MaterialTheme.typography.caption.copy(
                                    fontWeight = FontWeight.Medium
                                ),
                                color = androidx.compose.material.MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        // Facilities
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

                        Spacer(modifier = Modifier.height(24.dp))

                        // Action Buttons
                        Button(
                            onClick = onBookClick,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = room.status.lowercase() == "available",
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary
                            )
                        ) {
                            Icon(Icons.Default.DateRange, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Book Now")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { navController.navigate("booking_list/${room.id}") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary
                            )
                        ) {
                            Icon(Icons.Default.List, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View All Bookings")
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = { navController.navigate("review/${room.id}") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Primary
                            )
                        ) {
                            Icon(Icons.Default.Star, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("View Reviews")
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
            Text(
                text = facility,
                style = androidx.compose.material.MaterialTheme.typography.caption,
                color = Color.DarkGray,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


private fun Double.format(digits: Int) = "%.${digits}f".format(this)



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
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text("Retry")
        }
    }
}
