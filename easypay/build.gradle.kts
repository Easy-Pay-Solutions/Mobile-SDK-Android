plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.fm.easypay"
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        buildConfig = true
    }
    buildTypes {
        debug {
            // TODO: Replace URL with demo one
            buildConfigField(
                "String",
                "API_URL",
                "\"https://easypay5.com/APIcardProcMobile/\""
            )
            buildConfigField("String", "API_VERSION", "\"v1.0.0\"")
        }
        release {
            buildConfigField(
                "String",
                "API_URL",
                "\"https://easypay5.com/APIcardProcMobile/\""
            )
            buildConfigField("String", "API_VERSION", "\"v1.0.0\"")
            isMinifyEnabled = true
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

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.test:core-ktx:1.5.0")

    // Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    testImplementation("org.mockito:mockito-core:5.10.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")

    //Koin
    val koinVersion = "3.5.3"
    implementation("io.insert-koin:koin-android:$koinVersion")

    //Retrofit2
    val retrofit2Version = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:$retrofit2Version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit2Version")

    //OkHttp3
    val okhttp3Version = "4.12.0"
    implementation("com.squareup.okhttp3:okhttp:$okhttp3Version")
    implementation("com.squareup.okhttp3:logging-interceptor:$okhttp3Version")

    //RootBeer
    val rootbeerVersion = "0.1.0"
    implementation("com.scottyab:rootbeer-lib:$rootbeerVersion")
}