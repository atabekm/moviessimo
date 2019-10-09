package com.example.feature.list.presentation

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KSnackbar
import com.example.feature.list.R
import org.hamcrest.Matcher

class ListScreen : Screen<ListScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.movieRecycler)
    }, itemTypeBuilder = {
        itemType(::MovieItem)
    })
    val snackBar = KSnackbar()

    class MovieItem(parent: Matcher<View>) : KRecyclerItem<MovieItem>(parent) {
        val image: KImageView = KImageView { withMatcher(parent) }
    }
}
