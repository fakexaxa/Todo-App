plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.todo"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    dynamicFeatures += setOf(":feature_todo")

}

dependencies {
    api(project(":model-todo"))
    api(libs.bundles.main)
    testImplementation(libs.bundles.test.logic)
    androidTestImplementation(libs.bundles.test.ui)
}
