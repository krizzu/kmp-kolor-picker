plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.publish) apply false
    alias(libs.plugins.spotless)
    // alias(libs.plugins.validator.abi) apply false
}

spotless {
    val buildDirectory = layout.buildDirectory.asFileTree
    val licenseFile = rootProject.file("spotless/license.kt")
    kotlin {
        target("**/*.kt", "**/*.kts")
        ktfmt().kotlinlangStyle()
        targetExclude(buildDirectory, licenseFile, "**/*.gradle.kts")
        licenseHeaderFile(licenseFile)
        trimTrailingWhitespace()
        endWithNewline()
    }
}
