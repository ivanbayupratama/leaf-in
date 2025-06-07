package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.proyek.leaf_in.auth.register.RegisterScreen // 1. Perbaiki salah ketik di sini
import com.proyek.leaf_in.ui.theme.Leaf_inTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Leaf_inTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // 2. Panggil RegisterScreen dengan parameter yang dibutuhkan
                    RegisterScreen(
                        onRegistrationSuccess = {
                            // Karena ini hanya preview, biarkan kosong saja
                        }
                    )
                }
            }
        }
    }
}