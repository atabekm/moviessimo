object Android {
    const val compile_sdk_version = 28
    const val min_sdk_version = 19
    const val target_sdk_version = 28
    const val version_code = 1
    const val version_name = "0.1"
}

object Versions {
    const val appcompat = "1.0.2"
    const val constraint_layout = "1.1.3"
    const val espresso_core = "3.2.0"
    const val gradle = "3.6.0-alpha08"
    const val junit = "4.12"
    const val kotlin = "1.3.50"
    const val ktx = "1.0.2"
}

object Libs {
    const val kotlin_std = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout}"
}

object TestLibs {
    const val junit = "junit:junit:${Versions.junit}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso_core}"
}