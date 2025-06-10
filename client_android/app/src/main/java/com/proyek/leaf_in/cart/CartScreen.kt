// path: com/proyek/leaf_in/chart/CartScreen.kt

package com.proyek.leaf_in.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.proyek.leaf_in.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
     onBackClicked: () -> Unit,
     onCheckoutClicked: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val leafGreen = Color(0xFF89D133)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cart",
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                // --- TAMBAHKAN BLOK INI UNTUK MENYEIMBANGKAN JUDUL ---
                actions = {
                    // Spacer dengan lebar sama seperti IconButton untuk menyeimbangkan layout
                    Spacer(modifier = Modifier.width(48.dp))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = leafGreen,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        },
        containerColor = leafGreen
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(top = 16.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Text(
                text = "My Cart",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(uiState.cartItems, key = { it.id }) { item ->
                    CartItemRow(
                        item = item,
                        onIncrease = { viewModel.increaseQuantity(item.id) },
                        onDecrease = { viewModel.decreaseQuantity(item.id) }
                    )
                }
            }

            SummarySection(
                subTotal = uiState.subTotal,
                total = uiState.total
            )

            Button(
                onClick = { /* TODO: Aksi checkout */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = leafGreen)
            ) {
                Text(text = "CHECKOUT", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}


@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = item.imageUrl),
            contentDescription = item.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(
                text = "Rp. ${String.format("%,.0f", item.price)}",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onDecrease, modifier = Modifier.size(28.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.button_min),
                    contentDescription = "Decrease Quantity"
                )
            }
            Text(
                text = item.quantity.toString().padStart(2, '0'),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            IconButton(onClick = onIncrease, modifier = Modifier.size(28.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.button_tambah),
                    contentDescription = "Increase Quantity"
                )
            }
        }
    }
}

@Composable
fun SummarySection(subTotal: Double, total: Double) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Sub Total", fontSize = 16.sp, color = Color.Gray)
            Text(
                text = "Rp. ${String.format("%,.0f", total)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
        }
        Divider(modifier = Modifier.padding(vertical = 12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Total", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Rp. ${String.format("%,.0f", total)}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}