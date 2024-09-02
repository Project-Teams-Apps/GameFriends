// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val agp_version by extra("8.1.2")
}
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
    id("com.google.dagger.hilt.android") version "2.48" apply false
    kotlin("jvm") version "2.0.20" apply false
}