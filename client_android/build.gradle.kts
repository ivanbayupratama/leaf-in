// Top-level build file where you can add configuration options common to all sub-projects/modules.

// Ini adalah blok untuk mendefinisikan plugin yang digunakan di root project
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // JANGAN TAMBAHKAN PLUGIN HILT DI SINI. Plugin Hilt dideklarasikan di buildscript
    // Karena Hilt akan diterapkan ke modul individual (seperti 'app'),
    // Anda hanya perlu mendeklarasikan versi di dependencies buildscript.
}

// Ini adalah blok `buildscript` yang sangat penting untuk Hilt
// Ini mendefinisikan repositori dan dependensi yang dibutuhkan oleh Gradle itu sendiri
// untuk menemukan dan memuat plugin.
buildscript {
    repositories {
        google()        // Repositori Google Maven
        mavenCentral()  // Repositori Maven Central
    }
    dependencies {
        // Deklarasi Hilt Gradle Plugin di sini
        // Pastikan versi ini sama dengan versi yang Anda gunakan di app/build.gradle.kts
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
    }
}



// Anda mungkin akan melihat blok `dependencyResolutionManagement` di sini juga.
// Jika ada, pastikan repositori `google()` dan `mavenCentral()` juga ada di sana.
/*
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
*/