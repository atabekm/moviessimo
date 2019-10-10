package com.example.feature.details.presentation

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.rating.KRatingBar
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.feature.details.R
import com.example.feature.details.kakao.KAppBarLayout

class DetailsScreen : Screen<DetailsScreen>() {
    val appBar: KAppBarLayout = KAppBarLayout { withId(R.id.detailAppbar) }
    val backdrop: KImageView = KImageView { withId(R.id.detailBackdrop) }
    val toolbar: KView = KView { withId(R.id.detailToolbar) }
    val genres: KTextView = KTextView { withId(R.id.detailGenres) }
    val rating: KRatingBar = KRatingBar { withId(R.id.detailRating) }
    val duration: KTextView = KTextView { withId(R.id.detailDuration) }
    val overview: KTextView = KTextView { withId(R.id.detailOverview) }
    val director: KTextView = KTextView { withId(R.id.detailDirector) }
    val screenplay: KTextView = KTextView { withId(R.id.detailScreenplay) }
    val casting: KTextView = KTextView { withId(R.id.detailCasting) }
    val poster: KImageView = KImageView { withId(R.id.detailPoster) }
}
