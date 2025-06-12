// app/src/main/java/com/proyek/leaf_in/main/profile/EditProfileScreen.kt
package com.proyek.leaf_in.main.profile // <-- PASTIKAN INI SESUAI LOKASI FISIK FILE ANDA!

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.* // Penting untuk semua komponen Material3
import androidx.compose.runtime.* // Penting untuk remember, mutableStateOf, LaunchedEffect, dll.
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color // Penting untuk Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource // Penting untuk painterResource()
import androidx.compose.ui.res.stringResource // Penting untuk stringResource()
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel // Penting untuk hiltViewModel()
import coil.compose.rememberAsyncImagePainter // Penting untuk rememberAsyncImagePainter()
import com.proyek.leaf_in.R // Penting untuk mengakses R.drawable dan R.string

// IMPOR WARNA KUSTOM DARI ui.theme ANDA
import com.proyek.leaf_in.ui.theme.DarkGrayOutline
import com.proyek.leaf_in.ui.theme.LeafGreen
import com.proyek.leaf_in.ui.theme.LightBackground
import com.proyek.leaf_in.ui.theme.LightGrayOutline
import com.proyek.leaf_in.ui.theme.Leaf_inTheme // Untuk Preview Theme
import com.proyek.leaf_in.ui.theme.MintGreen

// IMPOR VIEWMODEL DAN UISTATE DARI PACKAGE YANG SAMA ATAU LOKASI YANG TEPAT
import com.proyek.leaf_in.main.profile.EditProfileUiState // Pastikan ini ada di lokasi yang benar
import com.proyek.leaf_in.main.profile.EditProfileViewModel // Pastikan ini ada di lokasi yang benar


@OptIn(ExperimentalMaterial3Api::class) // Anotasi ini dibutuhkan untuk TopAppBar, Scaffold, dll.
@Composable
fun EditProfileScreen(
    viewModel: EditProfileViewModel = hiltViewModel(),
    onBackClick: () -> Unit // Callback untuk kembali
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            viewModel.onProfilePictureSelected(uri)
        }
    )

    LaunchedEffect(key1 = uiState.errorMessage, key2 = uiState.isSuccess) {
        if (uiState.errorMessage != null) {
            Toast.makeText(context, uiState.errorMessage, Toast.LENGTH_SHORT).show()
            viewModel.errorShown()
        }
        if (uiState.isSuccess) {

            Toast.makeText(context, context.getString(R.string.profile_updated_success), Toast.LENGTH_SHORT).show()
            viewModel.successShown()
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            // Ini adalah fungsi Composable, dan dipanggil dalam konteks Composable Scaffold
            TopAppBar(
                title = { Text(stringResource(id = R.string.edit_profile_title)) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.Close, contentDescription = "Close")
                    }
                },
                // Kode ini sudah benar dan harusnya tidak menyebabkan error @Composable invocations
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF89D133),
                    titleContentColor = Color.Black // Menggunakan Color.Black (dari androidx.compose.ui.graphics.Color)
                )
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LightBackground)
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                            .height(250.dp)
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(top = 100.dp)
                    ) {
                        val painter = if (uiState.profilePictureUri != null) {
                            rememberAsyncImagePainter(uiState.profilePictureUri)
                        } else {
                            // R.drawable.ic_profile_placeholder
                            painterResource(id = R.drawable.ic_profile_placeholder)
                        }

                        Image(
                            painter = painter,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(2.dp, Color.White, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = stringResource(id = R.string.change_profile_photo),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            modifier = Modifier
                                .clickable { imagePickerLauncher.launch("image/*") }
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.LightGray.copy(alpha = 0.5f))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(text = stringResource(id = R.string.full_name_profile), color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.fullName,
                        onValueChange = viewModel::onFullNameChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkGrayOutline, unfocusedBorderColor = LightGrayOutline,
                            focusedLabelColor = DarkGrayOutline, unfocusedLabelColor = LightGrayOutline,
                            focusedTextColor = Color.Black, unfocusedTextColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray, focusedPlaceholderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = stringResource(id = R.string.email_address_profile), color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.email,
                        onValueChange = { /* Tidak bisa diedit */ },
                        readOnly = true,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkGrayOutline, unfocusedBorderColor = LightGrayOutline,
                            focusedLabelColor = DarkGrayOutline, unfocusedLabelColor = LightGrayOutline,
                            focusedTextColor = Color.Black, unfocusedTextColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray, focusedPlaceholderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = stringResource(id = R.string.phone_number_profile), color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.phoneNumber,
                        onValueChange = viewModel::onPhoneNumberChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkGrayOutline, unfocusedBorderColor = LightGrayOutline,
                            focusedLabelColor = DarkGrayOutline, unfocusedLabelColor = LightGrayOutline,
                            focusedTextColor = Color.Black, unfocusedTextColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray, focusedPlaceholderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(15.dp))

                    Text(text = stringResource(id = R.string.address_profile), color = Color.Black, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.address,
                        onValueChange = viewModel::onAddressChange,
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = DarkGrayOutline, unfocusedBorderColor = LightGrayOutline,
                            focusedLabelColor = DarkGrayOutline, unfocusedLabelColor = LightGrayOutline,
                            focusedTextColor = Color.Black, unfocusedTextColor = Color.Black,
                            unfocusedPlaceholderColor = Color.Gray, focusedPlaceholderColor = Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.height(40.dp))

                    Button(
                        onClick = viewModel::saveChanges,
                        enabled = !uiState.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp)
                            .height(50.dp)
                            .border(
                                width = 1.dp,
                                color = Color.White,
                                shape = RoundedCornerShape(50.dp)
                            ),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = LeafGreen,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                    ) {
                        if (uiState.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Black
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.save_changes),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 720)
@Composable
fun EditProfileScreenPreview() {
    Leaf_inTheme {
        EditProfileScreen(onBackClick = {})
    }
}