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
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// 1. TAMBAHKAN NavController SEBAGAI PARAMETER
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            Surface(shadowElevation = 20.dp) {
                // 2. PASS NavController KE BottomNavigationBar
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .background(Color(0xFFF0F0F0))
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

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else if (uiState.errorMessage != null) {
                Text(text = "Error: ${uiState.errorMessage}", color = Color.Red)
            } else {
                MenuSection(title = "Meals", items = uiState.meals)
                Spacer(modifier = Modifier.height(24.dp))
                MenuSection(title = "Beverages", items = uiState.beverages)
                Spacer(modifier = Modifier.height(16.dp))
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
            Text(text = "Show All", color = Color(0xFFED1A1A))
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
        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier
            .width(180.dp)
            .height(250.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(model = item.imageUrl),
                contentDescription = item.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color(0xFF89D133), shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp))
                    .padding(10.dp)
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
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
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

@Composable
// 3. TAMBAHKAN NavController SEBAGAI PARAMETER
fun BottomNavigationBar(navController: NavController) {
    // Kode untuk mendeteksi halaman/rute yang aktif saat ini
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color(0xFF89D133),
        modifier = Modifier.height(62.dp)
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = Color.Black,
            unselectedIconColor = Color.Black,
            selectedTextColor = Color.Black,
            unselectedTextColor = Color.Black
        )

        NavigationBarItem(
            // 4. BUAT 'selected' MENJADI DINAMIS
            selected = currentRoute == "home",
            // 5. ISI FUNGSI ONCLICK DENGAN NAVIGASI
            onClick = { navController.navigate("home") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.home_button),
                    contentDescription = "Home",
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text("Home") },
            colors = navItemColors
        )
        NavigationBarItem(
            selected = currentRoute == "chart",
            onClick = { navController.navigate("chart") },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.cart_button),
                    contentDescription = "Cart",
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text("Cart") },
            colors = navItemColors
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = { /* TODO: navController.navigate("profile") */ },
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(20.dp)
                )
            },
            label = { Text("Profile") },
            colors = navItemColors
        )
    }
}


// --- Bagian PREVIEW --- (Tidak ada perubahan fungsional, hanya perbaikan agar tidak error)

@Preview(showBackground = true, showSystemUi = true, name = "HomeScreen Preview")
@Composable
fun PreviewHomeScreen() {
    Leaf_inTheme {
        // Karena HomeScreen sekarang butuh NavController, kita buat dummy di preview
        HomeScreen(navController = rememberNavController())
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
        // Karena BottomNavigationBar sekarang butuh NavController, kita buat dummy di preview
        BottomNavigationBar(navController = rememberNavController())
    }
}