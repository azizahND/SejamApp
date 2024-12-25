import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextAlign

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }
}

// Warna kustom
val GreenBackground = Color(0xFF326A34)
val LightGrayBackground = Color(0xFFF5F5F5)
val WhiteBackground = Color(0xFFFFFFFF)
val GrayText = Color(0xFF808080)

// Halaman Profil
@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightGrayBackground)
    ) {
        // Header Hijau dengan Icon Profil
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .background(GreenBackground),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color.LightGray,
                modifier = Modifier.size(100.dp)
                .offset(y = (-30).dp)
            ) {
                // Placeholder untuk gambar profil
            }
        }

        // Kartu Putih dengan Informasi Profil
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .offset(y = (-70).dp),
            shape = RoundedCornerShape(12.dp),
            color = WhiteBackground,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(60.dp) // Menambahkan jarak antar ProfileItem
            ) {
                ProfileItem(label = "Nama", value = "Heni Yunida")
                ProfileItem(label = "Email", value = "heni1@gmail.com")
                ProfileItem(label = "NIM", value = "2211523008")
                ProfileItem(label = "Password", value = "wkwkwkwk")
            }
        }
            Button(
                onClick = {
                    // Fungsi onClick kosong karena belum ada navigasi atau aksi lebih lanjut
                },
                modifier = Modifier
                    .width(150.dp)
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 0.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF326A34)
                            // Warna hijau tombol
                )
            ) {
                Text(
                    text = "Edit Profil", // Teks tombol
                    color = Color.White,
                    style = androidx.compose.ui.text.TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                )
            }
        }
    }


// Komponen untuk setiap item profil
@Composable
fun ProfileItem(label: String, value: String) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Text(
            text = value,
            fontSize = 25.sp,
            color = GrayText
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}
