package com.proyek.leaf_in

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.proyek.leaf_in.auth.login.LoginScreen // <-- PASTIKAN IMPORT INI ADA
import com.proyek.leaf_in.ui.theme.Leaf_inTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() bisa kamu hapus atau beri comment jika tidak diperlukan
        setContent {
            Leaf_inTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Panggil LoginScreen kamu di sini
                    LoginScreen()
                }
            }
        }
    }
}