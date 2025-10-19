import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.publish)
}

kotlin {
    androidTarget { compilerOptions { jvmTarget.set(JvmTarget.JVM_11) } }

    listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    jvm("desktop")

    @OptIn(ExperimentalWasmDsl::class) wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
        }
    }

    @OptIn(org.jetbrains.kotlin.gradle.dsl.abi.ExperimentalAbiValidation::class)
    abiValidation { enabled.set(true) }
}

android {
    namespace = "com.kborowy.kolorpicker"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig { minSdk = libs.versions.android.minSdk.get().toInt() }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
