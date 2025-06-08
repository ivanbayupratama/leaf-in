package com.proyek.leaf_in.auth.login // Ganti dengan nama package kamu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyek.leaf_in.R
import com.proyek.leaf_in.ui.theme.DarkGreen

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = viewModel()) {
    // Mengambil state dari ViewModel dan otomatis recompose saat ada perubahan
    val uiState by loginViewModel.uiState.collectAsState()

    // Container utama
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
        // Tambahkan scrollable jika konten bisa lebih panjang dari layar
        // .verticalScroll(rememberScrollState())
    ) {
        // === Bagian Header dengan Gambar Latar ===
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg),
                contentDescription = "Top background wave",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 110.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.leafin),
                    contentDescription = "Leaf-in Logo",
                    modifier = Modifier.size(120.dp)
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Hello",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Sign into your Account",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // === Bagian Form Input ===
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "Email", color = Color.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { loginViewModel.onEmailChange(it) },
                placeholder = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = "Password", color = Color.Black, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                placeholder = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(25.dp))

            // === Bagian Link Register & Forgot Password ===
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "New here? Register",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { loginViewModel.onRegisterClick() }
                )
                Spacer(modifier = Modifier.weight(1f)) // Spacer untuk mendorong ke kanan
                Text(
                    text = "Forgot password?",
                    color = Color.Black,
                    fontSize = 14.sp,
                    modifier = Modifier.clickable { loginViewModel.onForgotPasswordClick() }
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            // === Tombol Login ===
            // Menggunakan Text yang diberi modifier, mirip seperti XML kamu
            // Alternatifnya bisa pakai Button(...)
            Text(
                text = "Login",
                color = Color.Black,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(50))
                    .background(Color(0xFF89D133))
                    .clickable { loginViewModel.onLoginClick() }
                    .padding(horizontal = 25.dp, vertical = 10.dp)
            )
        }
    }
}

// Composable untuk preview di Android Studio
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    // Kamu bisa membungkusnya dengan Theme project kamu jika ada
    LoginScreen()
}