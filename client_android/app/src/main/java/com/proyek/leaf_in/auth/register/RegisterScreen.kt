package com.proyek.leaf_in.auth.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.proyek.leaf_in.R
import androidx.compose.ui.text.font.FontWeight

@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel = viewModel(),
    onRegistrationSuccess: () -> Unit // Callback untuk navigasi setelah sukses
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    // Menangani side-effects: Tampilkan pesan error & navigasi
    LaunchedEffect(key1 = uiState.registrationError, key2 = uiState.isRegistrationSuccess) {
        if (uiState.registrationError != null) {
            Toast.makeText(context, uiState.registrationError, Toast.LENGTH_SHORT).show()
            viewModel.errorShown() // Reset error state setelah ditampilkan
        }
        if (uiState.isRegistrationSuccess) {
            Toast.makeText(context, "Registrasi Berhasil!", Toast.LENGTH_SHORT).show()
            onRegistrationSuccess() // Panggil callback navigasi
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp))

            // Logo and Title
            Image(
                painter = painterResource(id = R.drawable.leafin),
                contentDescription = stringResource(id = R.string.leaf_in_logo),
                modifier = Modifier.size(120.dp)
            )
            Text(
                text = stringResource(id = R.string.create_account),
                fontSize = 30.sp,
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(50.dp)) // Spacer antara header dan form

            // --- Form Fields ---
            OutlinedTextField(
                value = uiState.fullName,
                onValueChange = viewModel::onFullNameChange,
                label = { Text(stringResource(id = R.string.full_name)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                label = { Text(stringResource(id = R.string.email)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            Spacer(modifier = Modifier.height(16.dp))

            var passwordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(stringResource(id = R.string.password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility")
                    }
                }
            )
            Spacer(modifier = Modifier.height(16.dp))

            var confirmPasswordVisible by remember { mutableStateOf(false) }
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = viewModel::onConfirmPasswordChange,
                label = { Text(stringResource(id = R.string.confirm_password)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                singleLine = true,
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility")
                    }
                }
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = viewModel::register,
                enabled = !uiState.isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(50.dp),
                // --- PERUBAHAN UTAMA DI SINI ---
                shape = RoundedCornerShape(50.dp), // Membuat bentuk oval penuh
                colors = ButtonDefaults.buttonColors( // Mengatur warna tombol
                    containerColor = Color(0xFF89D133), // Latar belakang hijau cerah
                    contentColor = Color.Black // Warna teks hitam
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp) // Menghilangkan bayangan
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.Black // Warna progress indicator hitam agar kontras
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.register),
                        fontSize = 20.sp, // Ukuran font lebih besar
                        fontWeight = FontWeight.Bold, // Teks lebih tebal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    // Wrap dengan Theme aplikasi Anda agar preview sesuai
    // YourAppTheme {
    RegisterScreen(onRegistrationSuccess = {})
    // }
}
ini yg sebelumnya cb benerin yg hrs diganti