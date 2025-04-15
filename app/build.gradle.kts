plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)

    //hilt
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.ksp)
    alias(libs.plugins.serialization)

 }

android {
    namespace = "com.hfad.ballterritory"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.hfad.ballterritory"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {


    implementation(libs.kotlinx.serialization.json)
    //modules
    implementation(projects.features.coaches)
    implementation(projects.features.profile)
    implementation(projects.features.rent)
    implementation(projects.features.schedule)
    implementation(projects.features.main)
    implementation(projects.core.network)
   implementation(projects.core.database)
   implementation(projects.core.designsystem)
   implementation(projects.features.auth)
    implementation(projects.core.navigation)
    implementation(projects.core.data)
    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.navigationHiltCompose)

    //hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.javax)

    //viewModel
    implementation(libs.androidx.lifecycle.viewmodel)

implementation(libs.kotlin.stdlib)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}