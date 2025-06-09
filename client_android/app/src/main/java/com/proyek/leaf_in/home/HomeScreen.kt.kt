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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = { BottomNavigationBar() }
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
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Leaf-in",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF4CAF50)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hello, what can I get for you today?",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
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
            Text(text = "Show All", color = Color(0xFF4CAF50))
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
        shape = RoundedCornerShape(12.dp),
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
                    .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Rp. ${String.format("%,.0f", item.price)}",
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
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Order", color = Color.White, fontSize = 12.sp)
                    }
                    IconButton(onClick = { /* TODO: Show more options */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.desc_product),
                            contentDescription = "More options",
                            tint = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

// --- FUNGSI INI SUDAH DIPERBARUI ---
@Composable
fun BottomNavigationBar() {
    NavigationBar(
        containerColor = Color(0xFF4CAF50), // Warna hijau bottom nav
        modifier = Modifier.height(62.dp)
    ) {
        val navItemColors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent,
            selectedIconColor = Color.White,
            unselectedIconColor = Color.White,
            selectedTextColor = Color.White,
            unselectedTextColor = Color.White
        )

        NavigationBarItem(
            selected = true,
            onClick = { /* TODO: Navigate to Home */ },
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
            selected = false,
            onClick = { /* TODO: Navigate to Cart */ },
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
            selected = false,
            onClick = { /* TODO: Navigate to Profile */ },
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