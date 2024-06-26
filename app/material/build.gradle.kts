plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.whf.material"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.whf.material"
        minSdk = 27
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("com.android.support:design:28.0.0")
    implementation("de.hdodenhof:circleimageview:2.1.0")
    implementation("com.android.support:recyclerview-v7:24.2.1")
    implementation("com.android.support:cardview-v7:24.2.1")
    implementation("com.github.bumptech.glide:glide:3.7.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")
    implementation("com.google.android.material:material:1.0.0")
}