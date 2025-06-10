// path: com/proyek/leaf_in/meals/MealsScreen.kt

package com.proyek.leaf_in.meals

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem
import java.util.Locale

// Fungsi "Pintar" yang mengambil data
@Composable
fun MealsScreen(
    navController: NavController,
    viewModel: MealsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MealsScreenContent(
        uiState = uiState,
        onProductCardClicked = { menuItemId ->
            // TODO: Navigasi ke Detail Produk
        }
    )
}

// Fungsi "Bodoh" yang hanya menggambar UI
@Composable
fun MealsScreenContent(
    uiState: MealsUiState,
    onProductCardClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
    ) {
        // Header (Sama seperti di HomeScreen)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.leafin),
                contentDescription = "Leaf-in Logo",
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                painter = painterResource(id = R.drawable.nama),
                contentDescription = "Leaf-in Name",
                modifier = Modifier.height(30.dp)
            )
        }

        // Judul Kategori
        Text(
            text = "Meals",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Tampilan Konten (Loading, Error, atau Grid)
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally))
            }
            uiState.errorMessage != null -> {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            else -> {
                // Tampilan Grid 2 Kolom yang bisa di-scroll
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.meals) { item ->
                        MenuItemCard(
                            item = item,
                            onCardClicked = { onProductCardClicked(item.id) }
                        )
                    }
                }
            }
        }
    }
}

// Card Item (diambil dari HomeScreen agar bisa dipakai di sini)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemCard(
    item: MenuItem,
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit = {}
) {
    Card(
        onClick = onCardClicked,
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.width(180.dp)
    ) {
        Column {
            Image(
                painter = rememberAsyncImagePainter(model = item.imageUrl),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF89D133), shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp))
                    .padding(12.dp),
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp. ${String.format(Locale("id", "ID"), "%,.0f", item.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* TODO: Add to cart */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(36.dp)
                    ) {
                        Text(text = "Order", color = Color(0xFF173006), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                    IconButton(onClick = { /* TODO: Show more options */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.desc_product),
                            contentDescription = "More options",
                            tint = Color(0xFF173006)
                        )
                    }
                }
            }
        }
    }
}