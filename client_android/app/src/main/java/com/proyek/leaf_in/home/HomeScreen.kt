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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.proyek.leaf_in.R
import com.proyek.leaf_in.data.model.MenuItem
import com.proyek.leaf_in.navigation.Screen
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import com.proyek.leaf_in.utils.findDrawableResourceId
import java.util.Locale

/**
 * Composable "pintar" yang terhubung ke ViewModel.
 * Karena ViewModel sekarang memuat data statis secara otomatis saat dibuat,
 * Composable ini tidak perlu lagi memicu pengambilan data.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    // Blok LaunchedEffect telah dihapus karena ViewModel tidak lagi
    // memiliki fungsi refreshDataFromApi() dan sudah otomatis memuat data dummy.

    HomeScreenContent(
        uiState = uiState,
        onShowAllClicked = { category ->
            navController.navigate(Screen.ProductList.createRoute(category))
        },
        onProductCardClicked = { menuItemId ->
            // TODO: Kirim ID produk ke halaman detail saat sudah siap
            navController.navigate("product_details")
        }
    )
}

/**
 * Composable "bodoh" yang hanya bertanggung jawab untuk menampilkan UI
 * berdasarkan state yang diberikan. Tidak mengandung logika.
 */
@Composable
fun HomeScreenContent(
    uiState: HomeUiState,
    onShowAllClicked: (category: String) -> Unit,
    onProductCardClicked: (menuItemId: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF0F0F0))
            .padding(bottom = 16.dp)
    ) {
        // --- Bagian Header ---
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
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
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Hello, what can I get for you today?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- Logika Tampilan Konten Utama ---
        when {
            uiState.isLoading -> {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 64.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            uiState.errorMessage != null -> {
                Box(modifier = Modifier.fillMaxWidth().padding(vertical = 64.dp), contentAlignment = Alignment.Center) {
                    Text(text = "Error: ${uiState.errorMessage}", color = Color.Red, style = MaterialTheme.typography.bodyLarge)
                }
            }
            else -> {
                MenuSection(
                    title = "Meals",
                    items = uiState.meals,
                    onShowAllClicked = { onShowAllClicked("meals") },
                    onProductCardClicked = onProductCardClicked
                )
                Spacer(modifier = Modifier.height(24.dp))
                MenuSection(
                    title = "Beverages",
                    items = uiState.beverages,
                    onShowAllClicked = { onShowAllClicked("beverages") },
                    onProductCardClicked = onProductCardClicked
                )
            }
        }
    }
}

/**
 * Komponen reusable untuk menampilkan satu section menu (misal: Meals).
 */
@Composable
fun MenuSection(
    title: String,
    items: List<MenuItem>,
    onShowAllClicked: () -> Unit,
    onProductCardClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        TextButton(onClick = onShowAllClicked) {
            Text(text = "Show All", color = Color.Red)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

    if (items.isEmpty() && title.isNotEmpty()) { // Ditambah cek title agar tidak tampil di preview
        Box(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(100.dp)
                .background(Color.White.copy(alpha = 0.5f), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Tidak ada produk untuk kategori '$title'", color = Color.Gray)
        }
    } else {
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items, key = { it.id }) { item ->
                MenuItemCard(
                    item = item,
                    onCardClicked = { onProductCardClicked(item.id) }
                )
            }
        }
    }
}

/**
 * Komponen reusable untuk menampilkan satu kartu produk.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemCard(
    item: MenuItem,
    modifier: Modifier = Modifier,
    onCardClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val imageResId = item.findDrawableResourceId(context)

    Card(
        onClick = onCardClicked,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.width(160.dp)
    ) {
        Column {
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(110.dp)
                )
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth().height(110.dp).background(Color.LightGray.copy(alpha = 0.3f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "No image available", tint = Color.Gray)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                Text(text = item.name, fontWeight = FontWeight.Bold, maxLines = 1, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Rp. ${String.format(Locale("id", "ID"), "%,.0f", item.price)}", color = Color(0xFF89D133), fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* TODO: Add to cart */ },
                    modifier = Modifier.fillMaxWidth().height(36.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89D133))
                ) {
                    Text("Order", color = Color.White, fontSize = 12.sp)
                }
            }
        }
    }
}

/**
 * Fungsi Preview untuk development UI secara terisolasi.
 */
@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun HomeScreenContentPreview() {
    val dummyMeals = listOf(MenuItem("1", "Toast", "Meals", 25000.0, "toast"))
    val dummyBeverages = listOf(MenuItem("4", "Green Tea", "Beverages", 12000.0, "green_tea"))
    val dummyState = HomeUiState(meals = dummyMeals, beverages = dummyBeverages)

    Leaf_inTheme {
        HomeScreenContent(
            uiState = dummyState,
            onShowAllClicked = {},
            onProductCardClicked = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuSectionEmptyPreview() {
    Leaf_inTheme {
        MenuSection(title = "Meals", items = emptyList(), onShowAllClicked = {}, onProductCardClicked = {})
    }
}