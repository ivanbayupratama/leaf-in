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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem
import com.proyek.leaf_in.navigation.Screen

import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem

import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import java.util.Locale

// =================================================================================
// BAGIAN 1: FUNGSI UTAMA YANG TERHUBUNG KE VIEWMODEL ("PINTAR")
// =================================================================================

@Composable


// 1. TAMBAHKAN NavController SEBAGAI PARAMETER

fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {

    // Mengambil data dari ViewModel
    val uiState by viewModel.uiState.collectAsState()

    // Memanggil Composable yang hanya bertugas menggambar UI
    HomeScreenContent(
        uiState = uiState,
        onShowAllClicked = { category ->
            // Logika navigasi saat tombol "Show All" diklik
            navController.navigate(Screen.ProductList.createRoute(category))
        },
        onProductCardClicked = { menuItemId ->
            // TODO: Nanti di sini tambahkan navigasi ke halaman Detail Produk
        }
    )
}

// =================================================================================
// BAGIAN 2: FUNGSI YANG HANYA MENGGAMBAR TAMPILAN ("BODOH")
// =================================================================================

@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onShowAllClicked: (category: String) -> Unit,
    onProductCardClicked: (menuItemId: String) -> Unit
) {
    // Column utama yang bisa di-scroll
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF0F0F0))
            .padding(bottom = 16.dp) // Beri padding bawah agar tidak terlalu mepet
    ) {
        // --- Bagian Header ---
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))

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
                    painter = painterResource(id = R.drawable.logotanpanama),
                    contentDescription = "Leaf-in Logo",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(id = R.drawable.nama),
                    contentDescription = "Leaf-in Name",

                    modifier = Modifier.height(80.dp)

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
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Logika Tampilan Konten ---
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))

            }
            uiState.errorMessage != null -> {
                Text(
                    text = "Error: ${uiState.errorMessage}",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            else -> {
                // --- Bagian Meals ---
                MenuSection(
                    title = "Meals",
                    items = uiState.meals,
                    onShowAllClicked = { onShowAllClicked("meals") },
                    onProductCardClicked = onProductCardClicked
                )
                Spacer(modifier = Modifier.height(24.dp))

                // --- Bagian Beverages ---
                MenuSection(
                    title = "Beverages",
                    items = uiState.beverages,
                    onShowAllClicked = { onShowAllClicked("beverages") },
                    onProductCardClicked = onProductCardClicked
                )

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

// =================================================================================
// BAGIAN 3: KOMPONEN-KOMPONEN KECIL YANG DIPAKAI ULANG
// =================================================================================

@Composable
fun MenuSection(
    title: String,
    items: List<MenuItem>,
    onShowAllClicked: () -> Unit,
    onProductCardClicked: (String) -> Unit
) {
    // Baris untuk Judul dan tombol "Show All"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        TextButton(onClick = onShowAllClicked) {

        TextButton(onClick = { /* TODO: Navigate to All Menu items */ }) {

            Text(text = "Show All", color = Color(0xFFED1A1A))
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    // Daftar produk horizontal
    LazyRow(
        contentPadding = PaddingValues(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items) { item ->
            MenuItemCard(
                item = item,
                onCardClicked = { onProductCardClicked(item.id) }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemCard(
    item: MenuItem,
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit = {}
) {
    Card(

        onClick = onCardClicked, // Membuat seluruh kartu bisa diklik


        shape = RoundedCornerShape(26.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.width(180.dp)
    ) {

        Column {

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

                    .background(Color(0xFF89D133), shape = RoundedCornerShape(bottomStart = 26.dp, bottomEnd = 26.dp))
                    .padding(12.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start

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


// =================================================================================
// BAGIAN 4: FUNGSI KHUSUS UNTUK PREVIEW
// =================================================================================

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


@Preview(showBackground = true, name = "Halaman Home Lengkap")
@Composable

fun HomeScreenPreview() {
    val dummyMeals = listOf(
        MenuItem("1", "Toast Enak Sekali", "url", 25000.0, "Meals"),
        MenuItem("2", "Bubur Ayam", "url", 15000.0, "Meals")
    )
    val dummyBeverages = listOf(
        MenuItem("4", "Smoothies", "url", 25000.0, "Beverages"),
        MenuItem("6", "Green Tea", "url", 12000.0, "Beverages")
    )
    val dummyState = HomeUiState(meals = dummyMeals, beverages = dummyBeverages)


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
        HomeScreenContent(
            uiState = dummyState,
            onShowAllClicked = {},
            onProductCardClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Satu Kartu Produk")
@Composable
fun MenuItemCardPreview() {
    val dummyItem = MenuItem("1", "Nasi Goreng Sehat", "url", 30000.0, "Meals")
    Leaf_inTheme {

        MenuItemCard(item = dummyItem)

        // Karena BottomNavigationBar sekarang butuh NavController, kita buat dummy di preview
        BottomNavigationBar(navController = rememberNavController())

    }
}