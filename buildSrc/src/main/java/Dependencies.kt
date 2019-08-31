object Android {
    const val compile_sdk_version = 28
    const val min_sdk_version = 19
    const val target_sdk_version = 28
    const val version_code = 1
    const val version_name = "0.1"
}

object Modules {
    const val core_networks = ":core-network"
    const val core_prefs = ":core-prefs"
    const val feature_list = ":feature-list"
}

object Versions {
    const val appcompat = "1.0.2"
    const val constraint_layout = "1.1.3"
    const val espresso_core = "3.2.0"
    const val gradle = "3.6.0-alpha08"
    const val junit = "4.12"
    const val koin = "2.0.1"
    const val kotlin = "1.3.50"
    const val ktx = "1.0.2"
    const val retrofit = "2.6.1"
    const val okhttp = "3.12.1"
}

object Libs {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
    const val koin_core = "org.koin:koin-core:${Versions.koin}"
    const val koin_android = "org.koin:koin-android:${Versions.koin}"
    const val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
}

object TestLibs {
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val junit = "junit:junit:${Versions.junit}"
}