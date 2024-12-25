package com.example.tb.ui.screens



import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tb.R
import com.example.tb.ui.screens.State
import androidx.compose.ui.tooling.preview.Preview as Preview


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ReviewScreen(navController: NavHostController, State: State) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 30.dp, top = 150.dp, end = 30.dp),

        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    )
    {
        // Input untuk Email
        OutlinedTextField(
            value = State.review,
            onValueChange = { State.review = it },
            label = { Text("") },
            placeholder = { Text("") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp)
                .height(200.dp)
            ,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),
                unfocusedContainerColor = Color(0xFF527853).copy(alpha = 0.4f),

                ),
            shape = RoundedCornerShape(20.dp)

        )



        Button(

            onClick = {
                navController.navigate("ruangan")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
                .width(60.dp)
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF326A34)
            )
        ) {
            Text(
                text = "Simpan",
                color = Color.White,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                modifier = Modifier.padding(horizontal = 10.dp)
            )
        }
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 0.dp, top = 400.dp, end = 0.dp, bottom = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    )

    {
        Spacer(modifier = Modifier.height(20.dp))
        val image: Painter = painterResource(id = R.drawable.rate)
        Box(
            modifier = Modifier
                .fillMaxSize() // Pastikan Box memenuhi seluruh layar atau area induk
        ) {
            Image(
                painter = image,
                contentDescription = "rate",
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Menempatkan gambar di kanan bawah
                    .height(300.dp)
                    .width(300.dp)
                    .padding(0.dp)


            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewReviewScreen() {

    val navController = rememberNavController()
    val State = State()

    ReviewScreen(navController = navController, State = State)
}
