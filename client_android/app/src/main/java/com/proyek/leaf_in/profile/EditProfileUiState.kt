// app/src/main/java/com/proyek/leaf_in/main/profile/EditProfileUiState.kt
package com.proyek.leaf_in.main.profile

import android.net.Uri

/**
 * Data class yang merepresentasikan status UI dari layar Edit Profile.
 *
 * @param fullName Nama lengkap pengguna.
 * @param email Alamat email pengguna (seringkali tidak bisa diedit).
 * @param phoneNumber Nomor telepon pengguna.
 * @param address Alamat pengguna.
 * @param profilePictureUri URI gambar profil yang dipilih/sedang ditampilkan.
 * @param isLoading Status loading (true jika sedang menyimpan perubahan).
 * @param errorMessage Pesan error jika update profil gagal (null jika tidak ada error).
 * @param isSuccess Status keberhasilan update profil.
 * @param initialDataLoaded True jika data awal sudah dimuat.
 */
data class EditProfileUiState(
    val fullName: String = "",
    val email: String = "", // Email biasanya tidak diedit, hanya ditampilkan
    val phoneNumber: String = "",
    val address: String = "",
    val profilePictureUri: Uri? = null, // URI dari gambar profil (local/remote)
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
    val initialDataLoaded: Boolean = false
)