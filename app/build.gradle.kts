plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.thanhdang.approvalmatrix"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.thanhdang.approvalmatrix"
        minSdk = 26
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    val coreKtxVersion: String by project
    val appcompatVersion: String by project
    val materialVersion: String by project
    val constraintLayoutVersion: String by project
    val mapsVersion: String by project
    val locationVersion: String by project
    val junitVersion: String by project
    val androidxTestExtJunitVersion: String by project
    val espressoCoreVersion: String by project
    val socketIoClientVersion: String by project
    val gsonVersion: String by project
    val lifecycleVersion: String by project
    val glideVersion: String by project
    val lottieVersion: String by project
    val navigationVersion: String by project
    val retrofit2Version: String by project
    val countryCodePickerVersion: String by project
    val roomVersion: String by project
    val playServiceAdsVersion: String by project
    val shimmerVersion: String by project

    // testing
    implementation("androidx.core:core-ktx:$coreKtxVersion")
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidxTestExtJunitVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoCoreVersion")

    //firebase
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.google.firebase:firebase-analytics")

    //android X
    implementation("androidx.appcompat:appcompat:$appcompatVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

    implementation("com.google.android.material:material:$materialVersion")

    //lifecycle
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-process:$lifecycleVersion")

    // socket.io
    implementation("io.socket:socket.io-client:$socketIoClientVersion")

//    // gson
//    implementation("com.google.code.gson:gson:$gsonVersion")

    // google map services
    implementation("com.google.android.gms:play-services-maps:$mapsVersion")
    implementation("com.google.android.gms:play-services-location:$locationVersion")

    // glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    implementation("com.github.bumptech.glide:compiler:$glideVersion")

    //lottie animation
    implementation("com.airbnb.android:lottie:$lottieVersion")

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:$navigationVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navigationVersion")

    // retrofit2
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit2Version")

    // Country Code Picker
    implementation("com.hbb20:ccp:$countryCodePickerVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    kapt("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")

    // shimmer effect
    implementation("com.facebook.shimmer:shimmer:$shimmerVersion")


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}