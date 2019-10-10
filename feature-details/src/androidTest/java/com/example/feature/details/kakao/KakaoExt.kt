package com.example.feature.details.kakao

import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.agoda.kakao.common.assertions.BaseAssertions

fun BaseAssertions.hasAlpha(alpha: Float) {
    view.check(
        ViewAssertions.matches(
            ViewMatchers.withAlpha(alpha)))
}
