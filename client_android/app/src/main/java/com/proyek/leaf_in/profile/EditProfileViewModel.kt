// app/src/main/java/com/proyek/leaf_in/main/profile/EditProfileViewModel.kt
package com.proyek.leaf_in.main.profile

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// Import repository atau use case jika Anda memiliki ini
// import com.proyek.leaf_in.domain.repository.UserRepository // Contoh repository untuk user
// import com.proyek.leaf_in.util.ResultWrapper // Import ResultWrapper

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    // Contoh: Inject UserRepository atau use case yang relevan di sini
    // private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditProfileUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfileData()
    }

    private fun loadProfileData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // TODO: Ambil data profil dari repository atau database lokal
                // Contoh placeholder data:
                kotlinx.coroutines.delay(1000) // Simulasi loading data
                _uiState.update {
                    it.copy(
                        fullName = "Ivan Bayu Pratama",
                        email = "ivan.223040057@mail.unpas.ac.id",
                        phoneNumber = "081220632706",
                        address = "Jl. Merpati Hitam",
                        // profilePictureUri = Uri.parse("android.resource://com.proyek.leaf_in/drawable/default_profile_pic"), // Contoh URI gambar default
                        isLoading = false,
                        initialDataLoaded = true
                    )
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Gagal memuat data profil."
                ) }
            }
        }
    }

    fun onFullNameChange(name: String) {
        _uiState.update { it.copy(fullName = name) }
    }

    // Email biasanya tidak bisa diedit di UI, tapi state-nya ada
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    fun onPhoneNumberChange(number: String) {
        _uiState.update { it.copy(phoneNumber = number) }
    }

    fun onAddressChange(address: String) {
        _uiState.update { it.copy(address = address) }
    }

    fun onProfilePictureSelected(uri: Uri?) {
        _uiState.update { it.copy(profilePictureUri = uri) }
        // TODO: Anda mungkin ingin mengupload gambar ke server di sini
    }

    fun saveChanges() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, isSuccess = false) }

            val currentUiState = _uiState.value

            // --- Validasi Sederhana ---
            if (currentUiState.fullName.isBlank()) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Nama lengkap tidak boleh kosong.") }
                return@launch
            }
            // Tambahkan validasi lain sesuai kebutuhan (email format, phone number, dll.)

            try {
                // TODO: Panggil method di repository untuk update profil
                // val result = userRepository.updateProfile(
                //     currentUiState.fullName,
                //     currentUiState.phoneNumber,
                //     currentUiState.address,
                //     currentUiState.profilePictureUri // Kirim URI jika Anda ingin mengupload gambar
                // )

                // Simulasi proses penyimpanan
                kotlinx.coroutines.delay(2000)

                // Contoh: Jika penyimpanan berhasil
                // if (result is ResultWrapper.Success) {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                // } else if (result is ResultWrapper.Error) {
                // _uiState.update { it.copy(isLoading = false, errorMessage = result.message) }
                // }

            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Terjadi kesalahan tidak terduga saat menyimpan."
                ) }
            }
        }
    }

    /**
     * Dipanggil setelah error ditampilkan agar state error bisa direset.
     */
    fun errorShown() {
        _uiState.update { it.copy(errorMessage = null) }
    }

    /**
     * Dipanggil setelah sukses ditampilkan agar state sukses bisa direset.
     */
    fun successShown() {
        _uiState.update { it.copy(isSuccess = false) }
    }
}