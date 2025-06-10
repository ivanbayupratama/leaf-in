package com.proyek.leaf_in.di

import javax.inject.Qualifier
import kotlin.annotation.AnnotationRetention.RUNTIME

// Custom qualifier untuk CoroutineDispatcher yang digunakan untuk operasi IO
@Qualifier // Menandakan bahwa ini adalah qualifier Hilt
@Retention(RUNTIME) // Penting agar annotation tersedia saat runtime
annotation class IoDispatcher