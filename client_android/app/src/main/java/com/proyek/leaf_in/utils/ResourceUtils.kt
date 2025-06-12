package com.proyek.leaf_in.utils

import android.content.Context
import androidx.annotation.DrawableRes
import com.proyek.leaf_in.data.model.MenuItem

/**
 * File ini berisi fungsi-fungsi utilitas yang berhubungan dengan resource Android.
 */

/**
 * Sebuah "extension function" untuk kelas MenuItem.
 * Fungsinya adalah untuk menemukan ID resource drawable yang sesuai dari nama file
 * yang diberikan oleh API backend (imageResName).
 *
 * @param context Context Android yang dibutuhkan untuk mengakses resources.
 * @return ID integer dari resource drawable. Mengembalikan 0 jika resource tidak ditemukan
 *         atau jika imageResName null atau kosong, yang akan mencegah aplikasi crash.
 */
@DrawableRes
fun MenuItem.findDrawableResourceId(context: Context): Int {
    // 1. Validasi: Jika nama resource dari API itu null atau hanya spasi kosong,
    //    kembalikan 0 (tidak ada resource). isNullOrBlank() menangani kedua kasus.
    if (this.imageResName.isNullOrBlank()) {
        return 0
    }

    // 2. Ambil nama resource dari objek MenuItem.
    val cleanResourceName = this.imageResName.substringBeforeLast('.')

    // 3. Gunakan 'getIdentifier' untuk mencari ID resource secara dinamis.
    //    Parameter pertama adalah NAMA resource (tanpa ekstensi).
    //    Parameter kedua adalah TIPE resource ("drawable", "string", "color", dll).
    //    Parameter ketiga adalah package aplikasi.
    return context.resources.getIdentifier(
        cleanResourceName, // <-- Gunakan nama yang sudah dibersihkan
        "drawable",
        context.packageName
    )
}