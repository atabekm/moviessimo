package com.example.feature.details.compose

import androidx.annotation.DrawableRes
import androidx.compose.Composable
import androidx.compose.Model
import androidx.compose.effectOf
import androidx.compose.memo
import androidx.compose.onCommit
import androidx.compose.state
import androidx.compose.unaryPlus
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.ui.core.Text
import androidx.ui.core.WithDensity
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.graphics.Color
import androidx.ui.graphics.vector.DrawVector
import androidx.ui.layout.Column
import androidx.ui.layout.Container
import androidx.ui.layout.FlexColumn
import androidx.ui.layout.FlexRow
import androidx.ui.layout.Padding
import androidx.ui.material.MaterialTheme
import androidx.ui.material.TopAppBar
import androidx.ui.material.ripple.Ripple
import androidx.ui.res.vectorResource
import androidx.ui.tooling.preview.Preview
import com.example.core.network.model.Resource
import com.example.feature.details.R
import com.example.feature.details.domain.model.Movie

private val defaultSpacerSize = 16.dp

@Model
class MovieDetails(var movie: Movie = Movie.EMPTY)

@Composable
fun MovieDetailsScreen(data: LiveData<Resource<Movie>>, goToMoviesList: () -> Unit) {
    val model = +observe(data)
    val movie = model?.data ?: Movie.EMPTY

    MaterialTheme(colors = lightThemeColors, typography = themeTypography) {
        FlexColumn {
            inflexible {
                TopAppBar(
                    title = { Text(movie.title) },
                    navigationIcon = {
                        VectorImageButton(id = R.drawable.ic_baseline_arrow_back_24) {
                            goToMoviesList()
                        }
                    }
                )
            }
            flexible(flex = 1f) {
                VerticalScroller {
                    Padding(left = defaultSpacerSize, right = defaultSpacerSize) {
                        Column {
                            Padding(top = 8.dp, bottom = 8.dp) {
                                Text(movie.genres)
                            }
                            Text(movie.duration)
                            Padding(top = 8.dp) {
                                Text(text = "Overview", style = themeTypography.subtitle1)
                            }
                            Text(movie.overview)

                            FlexRow {
                                expanded(flex = 1f) {
                                    Column {
                                        Padding(top = 8.dp) {
                                            Text(text = "Director", style = themeTypography.subtitle1)
                                        }
                                        Text(movie.director)
                                    }
                                }
                                expanded(flex = 1f) {
                                    Column {
                                        Padding(top = 8.dp) {
                                            Text(text = "Screenplay", style = themeTypography.subtitle1)
                                        }
                                        Text(movie.screenplay)
                                    }
                                }
                            }

                            Padding(top = 8.dp) {
                                Text(text = "Cast", style = themeTypography.subtitle1)
                            }
                            Text(text = movie.cast)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieDetailsPreview() {
    MovieDetailsScreen(MutableLiveData(Resource.success(Movie.EMPTY))) {}
}

@Composable
fun VectorImageButton(@DrawableRes id: Int, onClick: () -> Unit) {
    Ripple(bounded = false) {
        Clickable(onClick = onClick) {
            VectorImage(id)
        }
    }
}

@Composable
fun VectorImage(@DrawableRes id: Int, tint: Color = Color.Transparent) {
    val vector = +vectorResource(id)
    WithDensity {
        Container(
            width = vector.defaultWidth.toDp(),
            height = vector.defaultHeight.toDp()
        ) {
            DrawVector(vector, tint)
        }
    }
}

fun <T> observe(data: LiveData<T>) = effectOf<T?> {
    val result = +state { data.value }
    val observer = +memo { Observer<T> { result.value = it } }

    +onCommit(data) {
        data.observeForever(observer)
        onDispose { data.removeObserver(observer) }
    }

    result.value
}
