object Android {
    const val compile_sdk_version = 33
    const val min_sdk_version = 24
    const val target_sdk_version = 33
    const val version_code = 1
    const val version_name = "0.1"
}

object Modules {
    const val core_mvi = ":core-mvi"
    const val core_networks = ":core-network"
    const val core_prefs = ":core-prefs"
    const val core_testing = ":core-testing"
    const val core_utils = ":core-utils"
    const val feature_details = ":feature-details"
    const val feature_list = ":feature-list"
}

object Versions {
    const val appcompat = "1.5.1"
    const val coil = "2.2.2"
    const val compose = "1.2.1"
    const val compose_compiler = "1.3.2"
    const val compose_activity = "1.5.1"
    const val core_testing = "2.1.0"
    const val coroutines = "1.6.4"
    const val espresso_core = "3.4.0"
    const val gradle = "7.3.0"
    const val gson = "2.9.1"
    const val junit = "4.13.2"
    const val kakao = "2.4.0"
    const val koin = "3.2.2"
    const val koin_compose = "3.2.1"
    const val kotlin = "1.7.20"
    const val ktx = "1.9.0"
    const val lifecycle = "2.6.0-alpha02"
    const val mockk = "1.13.2"
    const val navigation = "2.5.2"
    const val okhttp = "4.10.0"
    const val orbit = "4.3.2"
    const val retrofit = "2.9.0"
    const val test_core = "1.4.0"
    const val test_ext = "1.1.3"
    const val test_rules = "1.4.0"
    const val test_runner = "1.4.0"
}

object Libs {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val coil_compose = "io.coil-kt:coil-compose:${Versions.coil}"
    const val compose_activity = "androidx.activity:activity-compose:${Versions.compose_activity}"
    const val compose_lifecycle = "androidx.lifecycle:lifecycle-runtime-compose:${Versions.lifecycle}"
    const val compose_material = "androidx.compose.material:material:${Versions.compose}"
    const val compose_navigation = "androidx.navigation:navigation-compose:${Versions.navigation}"
    const val compose_ui = "androidx.compose.ui:ui:${Versions.compose}"
    const val compose_ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
    const val compose_ui_tooling_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val koin = "io.insert-koin:koin-android:${Versions.koin}"
    const val koin_compose = "io.insert-koin:koin-androidx-compose:${Versions.koin_compose}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val lifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.lifecycle}"
    const val okhttp_logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val orbit = "org.orbit-mvi:orbit-viewmodel:${Versions.orbit}"
    const val orbit_compose = "org.orbit-mvi:orbit-compose:${Versions.orbit}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val retrofit_gson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
}

object TestLibs {
    const val core_testing = "androidx.arch.core:core-testing:${Versions.core_testing}"
    const val coroutines_test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val espresso_contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso_core}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
    const val junit = "junit:junit:${Versions.junit}"
    const val kakao = "com.agoda.kakao:kakao:${Versions.kakao}"
    const val koin_test = "io.insert-koin:koin-test:${Versions.koin}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val mockk_android = "io.mockk:mockk-android:${Versions.mockk}"
    const val orbit = "org.orbit-mvi:orbit-test:${Versions.orbit}"
    const val test_core = "androidx.test:core:${Versions.test_core}"
    const val test_ext = "androidx.test.ext:junit:${Versions.test_ext}"
    const val test_rules = "androidx.test:rules:${Versions.test_rules}"
    const val test_runner = "androidx.test:runner:${Versions.test_runner}"
}
