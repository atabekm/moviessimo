package com.example.feature.details.kakao

import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import com.agoda.kakao.common.actions.BaseActions
import com.google.android.material.appbar.AppBarLayout
import org.hamcrest.Matchers

interface AppBarLayoutActions : BaseActions {

    fun expand() {
        view.perform(object : ViewAction {
            override fun getDescription() = "Expand AppBarLayout"

            override fun getConstraints() = Matchers.allOf(ViewMatchers.isAssignableFrom(AppBarLayout::class.java), ViewMatchers.isDisplayed())

            override fun perform(uiController: UiController?, view: View?) {
                if (view is AppBarLayout) {
                    view.setExpanded(true, true)
                }
            }
        })
    }

    fun collapse() {
        view.perform(object : ViewAction {
            override fun getDescription() = "Collapse AppBarLayout"

            override fun getConstraints() = Matchers.allOf(ViewMatchers.isAssignableFrom(AppBarLayout::class.java), ViewMatchers.isDisplayed())

            override fun perform(uiController: UiController?, view: View?) {
                if (view is AppBarLayout) {
                    view.setExpanded(false, true)
                }
            }
        })
    }
}
