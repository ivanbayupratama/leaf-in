package com.proyek.leaf_in.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem
import com.proyek.leaf_in.ui.theme.Leaf_inTheme // Pastikan import tema Anda benar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            // Dibungkus dengan Surface untuk efek shadow
            Surface(shadowElevation = 20.dp) {
                BottomNavigationBar()
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF0F0F0)) // Warna background sesuai desain
                .padding(16.dp)
        ) {
            // Header Section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leafin),
                    contentDescription = "Leaf-in Logo",
                    modifier = Modifier.size(80.dp) // Ukuran logo
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.nama), // Gambar nama "Leaf-in"
                    contentDescription = "Leaf-in Name",
                    modifier = Modifier.size(85.dp) // Ukuran nama
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Hello, what can I get for you today?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.errorMessage != null) {
                Text(text = "Error: ${uiState.errorMessage}", color = Color.Red)
            } else {
                // Meals Section
                MenuSection(title = "Meals", items = uiState.meals)
                Spacer(modifier = Modifier.height(24.dp))

                // Beverages Section
                MenuSection(title = "Beverages", items = uiState.beverages)

                Spacer(modifier = Modifier.height(16.dp)) // Tambahan spacer di bawah beverages
            }
        }
    }
}

@Composable
fun MenuSection(title: String, items: List<MenuItem>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        TextButton(onClick = { /* TODO: Navigate to All Menu items */ }) {
            Text(text = "Show All", color = Color(0xFFED1A1A)) // Warna merah "Show All"
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { item ->
            MenuItemCard(item = item)
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItem) {
    Card(
        shape = RoundedCornerShape(26.dp), // Radius sudut untuk Card
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        // Warna container utama Card tetap putih
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Gambar Produk
            Image(
                painter = rememberAsyncImagePainter(model = item.imageUrl),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Tinggi gambar
                    // Radius sudut atas gambar sesuai dengan Card
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
            )

            // Area Hijau Lemon untuk Detail Produk
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Mengambil sisa ruang vertikal
                    // Background hijau lemon dengan radius sudut bawah Card
                    .background(Color(0xFF89D133), shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp))
                    .padding(10.dp) // Padding di dalam area hijau lemon
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black, // Warna teks di atas hijau
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp. ${String.format("%,.0f", item.price)}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black // Warna teks di atas hijau
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { /* TODO: Add to cart */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Tombol 'Order' menjadi putih
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Order", color = Color(0xFF173006), fontSize = 12.sp, fontWeight = FontWeight.Bold) // Warna teks tombol 'Order'
                    }
                    IconButton(onClick = { /* TODO: Show more options */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.desc_product),
                            contentDescription = "More options",
                            tint = Color(0xFF173006) // Warna ikon '...'
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF89D133), // Warna hijau lemon untuk bottom nav
        modifier = Modifier.height(62.dp) // Tinggi bottom nav
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent, // Hilangkan indikator saat item dipilih
            selectedIconColor = Color.Black, // Warna ikon saat dipilih
            unselectedIconColor = Color.Black, // Warna ikon saat tidak dipilih
            selectedTextColor = Color.Black, // Warna teks saat dipilih
            unselectedTextColor = Color.Black // Warna teks saat tidak dipilih
        )

        NavigationBarItem(
            selected = true,
            onClick = { /* TODO: Navigate to Home */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_button),
                    contentDescription = "Home",
                    modifier = Modifier.size(20.dp) // Ukuran ikon
                )
            },
            label = { Text("Home") },
            colors = navItemColors
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Navigate to Cart */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.cart_button),
                    contentDescription = "Cart",
                    modifier = Modifier.size(20.dp) // Ukuran ikon
                )
            },
            label = { Text("Cart") },
            colors = navItemColors
        )
        NavigationBarItem(
            selected = false,
            onClick = { /* TODO: Navigate to Profile */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(20.dp) // Ukuran ikon
                )
            },
            label = { Text("Profile") },
            colors = navItemColors
        )
    }
}

// --- Bagian PREVIEW ---

@Preview(showBackground = true, showSystemUi = true, name = "HomeScreen Preview")
@Composable
fun PreviewHomeScreen() {
    // Pastikan ini adalah nama tema aplikasi Anda yang benar
    Leaf_inTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .padding(16.dp)
        ) {
            // Header Section (dari HomeScreen Anda)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logotanpanama),
                    contentDescription = "Leaf-in Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.nama),
                    contentDescription = "Leaf-in Name",
                    modifier = Modifier.size(85.dp)
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Hello, what can I get for you today?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Data Dummy untuk Preview
            val dummyMeals = listOf(
                MenuItem(id = "1", name = "Toast", imageUrl = "https://via.placeholder.com/150/FFD700/000000?text=Toast", price = 25000.0, category = "Meals"),
                MenuItem(id = "2", name = "Bubur Ayam", imageUrl = "https://via.placeholder.com/150/ADD8E6/000000?text=Bubur", price = 15000.0, category = "Meals"),
                MenuItem(id = "3", name = "Salad", imageUrl = "https://via.placeholder.com/150/90EE90/000000?text=Salad", price = 40000.0, category = "Meals"),
            )
            val dummyBeverages = listOf(
                MenuItem(id = "4", name = "Smoothies", imageUrl = "https://via.placeholder.com/150/FFB6C1/000000?text=Smoothies", price = 25000.0, category = "Beverages"),
                MenuItem(id = "5", name = "Jamu Beras Kencur", imageUrl = "https://via.placeholder.com/150/F0E68C/000000?text=Jamu", price = 8000.0, category = "Beverages"),
                MenuItem(id = "6", name = "Green Tea", imageUrl = "https://via.placeholder.com/150/8FBC8F/000000?text=Green+Tea", price = 12000.0, category = "Beverages"),
            )

            // Meals Section
            MenuSection(title = "Meals", items = dummyMeals)
            Spacer(modifier = Modifier.height(24.dp))

            // Beverages Section
            MenuSection(title = "Beverages", items = dummyBeverages)

            Spacer(modifier = Modifier.height(16.dp))
        }
        // Untuk melihat Bottom Nav di Preview HomeScreen, Anda bisa membungkusnya dalam Scaffold
        // atau membuat Preview terpisah untuk BottomNavigationBar seperti di bawah.
    }
}

@Preview(showBackground = true, name = "MenuItemCard Preview")
@Composable
fun PreviewMenuItemCard() {
    Leaf_inTheme {
        val dummyItem = MenuItem(
            id = "7",
            name = "Nasi Goreng Spesial",
            imageUrl = "https://via.placeholder.com/150/FF6347/000000?text=Nasi+Goreng",
            price = 30000.0,
            category = "Meals"
        )
        MenuItemCard(item = dummyItem)
    }
}

@Preview(showBackground = true, name = "BottomNav Preview")
@Composable
fun PreviewBottomNavigationBar() {
    Leaf_inTheme {
        BottomNavigationBar()
    }
}