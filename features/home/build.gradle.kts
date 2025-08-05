plugins {


    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)


    //hilt
    alias(libs.plugins.dagger.hilt.plugin)
    alias(libs.plugins.ksp)

    alias(libs.plugins.serialization)
}

android {
    namespace = "com.hfad.main"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
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

}

dependencies {
    //modules


    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.core.navigation)
    implementation(projects.core.data)

    implementation(libs.kotlinx.serialization.json)

    //navigation
    implementation(libs.navigation.compose)
    implementation(libs.navigationHiltCompose)

//hilt
    implementation(libs.dagger.hilt)
    ksp(libs.dagger.hilt.compiler)
    implementation(libs.javax)


    //network
    implementation(libs.retrofit2)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    //coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)


    implementation(libs.ui.text.google.fonts)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.transport.api)
    testImplementation(libs.junit)
    implementation(libs.androidx.material3.android)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
configurations.all {
    exclude(group = "com.intellij", module = "annotations")
}
