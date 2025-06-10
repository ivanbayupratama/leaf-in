// File: productdetails/ProductDetailsScreen.kt
// Versi FINAL - Menangani resource gambar yang null

package com.proyek.leaf_in.productdetails

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.proyek.leaf_in.R
import com.proyek.leaf_in.ui.theme.Leaf_inTheme
import java.text.NumberFormat
import java.util.Locale

// Fungsi helper untuk format harga ke Rupiah
fun formatPrice(price: Long): String {
    val format = NumberFormat.getCurrencyInstance(Locale("in", "ID"))
    val symbols = (format as java.text.DecimalFormat).decimalFormatSymbols
    symbols.currencySymbol = "Rp. "
    format.decimalFormatSymbols = symbols
    format.maximumFractionDigits = 0
    return format.format(price)
}

@Composable
fun ProductDetailsScreen(
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {} // Anda bisa tambahkan AppBottomNavigation() di sini jika perlu
    ) { paddingValues ->
        val product = uiState.product
        if (product != null) {
            ProductContent(
                modifier = Modifier.padding(paddingValues),
                product = product,
                onAddToCartClicked = { viewModel.onAddToCartClicked() }
            )
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator()
                } else {
                    Text("Produk tidak ditemukan.")
                }
            }
        }
    }
}

@Composable
fun ProductContent(
    modifier: Modifier = Modifier,
    product: Product,
    onAddToCartClicked: () -> Unit
) {
    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {
        // Latar belakang sekarang selalu menggunakan warna solid
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(320.dp)
                .background(Color(0xFF89D133))
        ) {
            // Logo tetap ditampilkan di atas latar belakang
            Row(
                modifier = Modifier
                    .padding(start = 24.dp, top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Tambahkan pengecekan null sebelum menampilkan gambar
                if (product.logoIconRes != null) {
                    Image(
                        painter = painterResource(id = product.logoIconRes),
                        contentDescription = "Logo Icon",
                        modifier = Modifier.height(100.dp)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                if (product.logoNameRes != null) {
                    Image(
                        painter = painterResource(id = product.logoNameRes),
                        contentDescription = "Logo Name",
                        modifier = Modifier.height(100.dp),
                        contentScale = ContentScale.Fit
                    )
                }
            }
        }

        Column(
            modifier = Modifier.verticalScroll(scrollState)
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                    .padding(top = 200.dp)
                    .padding(horizontal = 24.dp, vertical = 24.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = product.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray,
                    lineHeight = 24.sp
                )
                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Price",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = formatPrice(product.price),
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Button(
                        onClick = onAddToCartClicked,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF94C83D)
                        ),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Add to Cart Icon",
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Add to Cart",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Tambahkan pengecekan null sebelum menampilkan gambar
        if (product.imageRes != null) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 150.dp)
                    .size(240.dp)
                    .clip(CircleShape)
                    .shadow(elevation = 8.dp, shape = CircleShape)
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true, name = "Product Details Preview")
@Composable
fun ProductDetailsScreenPreview() {
    val mockProduct = Product(
        id = 1L,
        name = "Bubur Ayam",
        description = "Warm rice porridge topped with shredded chicken, fried shallots, soy sauce, and crispy crackers. Light, tasty, and comforting – perfect any time of day.",
        price = 15000L,
        imageRes = R.drawable.bubur_ayam,
        logoIconRes = R.drawable.logotanpanama,
        logoNameRes = R.drawable.nama
    )

    Leaf_inTheme {
        Scaffold { paddingValues ->
            ProductContent(
                modifier = Modifier.padding(paddingValues),
                product = mockProduct,
                onAddToCartClicked = {}
            )
        }
    }
}