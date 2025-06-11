package com.proyek.leaf_in.checkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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

// Fungsi helper untuk format harga
fun formatPrice(price: Double): String {
    return "Rp. ${String.format(Locale("id", "ID"), "%,.0f", price)}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    onBackClicked: () -> Unit,
    onOrderPlaced: () -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF89D133),
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        CheckoutContent(
            modifier = Modifier.padding(paddingValues),
            uiState = uiState,
            onPlaceOrderClicked = {
                viewModel.placeOrder()
                onOrderPlaced()
            }
        )
    }
}

@Composable
fun CheckoutContent(
    modifier: Modifier = Modifier,
    uiState: CheckoutUiState,
    onPlaceOrderClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF0F0F0))
            .verticalScroll(rememberScrollState())
    ) {
        // Alamat Pengiriman
        DeliveryAddressSection(address = uiState.deliveryAddress)

        // Ringkasan Pesanan
        OrderSummarySection(items = uiState.cartItems)

        // Metode Pembayaran
        PaymentMethodSection(
            selectedMethod = uiState.paymentMethod,
            onMethodSelected = {} // TODO: Implement payment method change
        )

        // Rincian Pembayaran
        PaymentDetailsSection(subTotal = uiState.subTotal, deliveryFee = uiState.deliveryFee, total = uiState.total)

        // Tombol Place Order
        Button(
            onClick = onPlaceOrderClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF89D133))
        ) {
            Text("PLACE ORDER", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
fun DeliveryAddressSection(address: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.Top // Mengubah alignment menjadi Top
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Alamat",
                tint = Color.Black,
                modifier = Modifier
                    .size(32.dp)
                    .padding(top = 4.dp) // Sedikit padding agar ikon sejajar dengan teks
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Kolom alamat diberi weight(1f) agar fleksibel dan tidak mendorong teks "Change"
            Column(modifier = Modifier.weight(1f)) {
                Text("Delivery Address", color = Color.Gray, fontSize = 14.sp)
                Text(address, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            }

            Spacer(modifier = Modifier.width(8.dp)) // Memberi sedikit jarak

            Text(
                text = "Change",
                color = Color(0xFFED1A1A),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.clickable { /* TODO: Implement change address */ }
            )
        }
    }
}

@Composable
fun OrderSummarySection(items: List<CartItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text("Order Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        items.forEach { item ->
            OrderItem(item = item)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun OrderItem(item: CartItem) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // --- PERBAIKAN DI SINI ---
        // Menghapus logika placeholder untuk sementara
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.name, fontWeight = FontWeight.Bold)
            Text(formatPrice(item.price), color = Color.Gray)
        }
        Text("x${item.quantity}", fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PaymentMethodSection(selectedMethod: String, onMethodSelected: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .clickable { /* TODO */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(16.dp))
            Text(selectedMethod, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.Black)
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Default.MoreHoriz,
                contentDescription = "Pilihan Lain",
                tint = Color.Gray
            )
        }
    }
}

@Composable
fun PaymentDetailsSection(subTotal: Double, deliveryFee: Double, total: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Sub Total", color = Color.Gray)
            Text(formatPrice(subTotal), fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Delivery Fee", color = Color.Gray)
            Text(formatPrice(deliveryFee), fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Divider()
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Total", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(formatPrice(total), fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    Leaf_inTheme {
        CheckoutScreen(onBackClicked = {}, onOrderPlaced = {})
    }
}
