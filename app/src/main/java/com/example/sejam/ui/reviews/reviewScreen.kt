package com.example.sejam.ui.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.sejam.ViewModelFactory
import com.example.sejam.data.response.ReviewsItem
import com.example.sejam.ui.theme.Primary
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ReviewScreen(
    roomId: String,
    navController: NavController,
    viewModel: ReviewViewModel = viewModel(factory = ViewModelFactory.getInstance(LocalContext.current))
) {
    val roomData by viewModel.reviews.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    var newComment by remember { mutableStateOf("") }

    LaunchedEffect(roomId) {
        viewModel.getReviews(roomId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
    ) {
        // Header Section
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(16.dp)
        ) {
            roomData?.let { room ->
                Text(
                    text = room.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "${room.reviews?.size ?: 0} Reviews",
                    style = MaterialTheme.typography.subtitle1,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }

        // Content Section
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Add review section
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = newComment,
                        onValueChange = { newComment = it },
                        label = { Text("Write your review") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        minLines = 3,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Primary,
                            focusedLabelColor = Primary
                        )
                    )

                    Button(
                        onClick = {
                            if (newComment.isNotBlank()) {
                                viewModel.createReview(roomId, newComment)
                                newComment = ""
                            }
                        },
                        modifier = Modifier.align(Alignment.End),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Primary
                        )
                    ) {
                        Icon(
                            Icons.Default.Send,
                            contentDescription = "Submit",
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Submit Review")
                    }
                }
            }

            // Reviews Section
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    color = Primary
                )
            } else {
                roomData?.reviews?.let { reviews ->
                    if (reviews.isEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                        ) {
                            Text(
                                text = "Be the first to write a review!",
                                modifier = Modifier.padding(24.dp),
                                style = MaterialTheme.typography.body1,
                                color = Color.Gray
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(reviews) { review ->
                                ReviewItem(review = review)
                            }
                        }
                    }
                }
            }

            errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colors.error,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ReviewItem(review: ReviewsItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                review.user?.let { user ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // User Avatar (circular background with first letter)
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Primary),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = user.username.firstOrNull()?.uppercase() ?: "U",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = user.username,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Text(
                    text = formatDate(review.createdAt),
                    style = MaterialTheme.typography.caption,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Divider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = review.comment,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

private fun formatDate(dateString: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = inputFormat.parse(dateString)
        outputFormat.format(date!!)
    } catch (e: Exception) {
        dateString
    }
}