# moviessimo
[![Build Status](https://travis-ci.org/atabekm/moviessimo.svg?branch=master)](https://travis-ci.org/atabekm/moviessimo)
[![Checked with ktlint](https://img.shields.io/badge/code%20style-%E2%9D%A4-FF4081.svg)](https://ktlint.github.io/)

Android sample app to experiment with various libraries/frameworks/technologies

## Project specifications

Project features:

* 100% [Kotlin](https://kotlinlang.org/)
* [Android Jetpack](https://developer.android.com/jetpack) (ViewModel, LiveData, Lifecycle)
* Clean architecture
* Multi-modular approach
* Single activity (with [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started) to navigate between fragments)
* Coroutines
* Dependency injection (service locator)
* Unit and UI tests

## Tech stack

<img src="screencast.gif" width="336" align="right" hspace="20">

Libraries used in the app:

* Google + JetBrains
    * [Kotlin](https://kotlinlang.org/)
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
    * [Jetpack](https://developer.android.com/jetpack)
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata)
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle)
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)
        * [Navigation](https://developer.android.com/topic/libraries/architecture/navigation/)
* Dependency injection
    * [Koin](https://insert-koin.io/)
* Network + Image
    * [Retrofit](https://square.github.io/retrofit/)
    * [Coil](https://github.com/coil-kt/coil)
* Tests
    * [Unit Tests](https://en.wikipedia.org/wiki/Unit_testing) ([JUnit4](https://junit.org/junit4/))
    * [Mockk](https://mockk.io/)
    * [Espresso](https://developer.android.com/training/testing/espresso/)
    * [Kakao](https://github.com/agoda-com/Kakao)
* Gradle
    * [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
* Static analysis tools
    * [ktlint](https://ktlint.github.io/)
    * [detekt](https://arturbosch.github.io/detekt/)
* Continuous integration
    * [Travis CI](https://travis-ci.org/)

## Experiments

Here are the experiments with latest libraries/technologies:

* [experiment/jetpack-compose](https://github.com/atabekm/moviessimo/tree/experiment/jetpack-compose) branch: trying to play with [Jetpack Compose](https://developer.android.com/jetpack/compose).
The library is still in early stages, so only basic text manipulation is covered.