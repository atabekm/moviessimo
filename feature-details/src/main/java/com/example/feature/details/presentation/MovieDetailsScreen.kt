package com.example.feature.details.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.feature.details.R
import com.example.feature.details.domain.model.Movie
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun MovieDetailsScreen(movieId: String?, onBackPressed: () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            MovieDetails(
                movieId = movieId ?: "",
                viewModel = getViewModel(),
                scope = scope,
                scaffoldState = scaffoldState
            )
        }
    }
}

@Composable
internal fun MovieDetails(
    movieId: String,
    viewModel: DetailsViewModel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect {
        handleSideEffect(
            it,
            scope,
            scaffoldState
        ) { viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId.toInt())) }
    }

    state.movie?.let { movie: Movie ->
        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = movie.title, modifier = Modifier.padding(top = 16.dp), style = MaterialTheme.typography.h5)
                    Text(text = movie.genres, modifier = Modifier.padding(top = 16.dp))
                    RatingBar(
                        rating = movie.rating,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .height(16.dp),
                        color = Color.Magenta
                    )
                    Text(text = movie.duration, modifier = Modifier.padding(top = 16.dp))
                    Text(text = stringResource(id = R.string.overview), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
                }
                AsyncImage(
                    model = movie.posterImage,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .width(120.dp)
                        .padding(top = 16.dp),
                    contentScale = ContentScale.FillWidth
                )
            }
            Text(text = movie.overview, modifier = Modifier.padding(top = 8.dp))
            Row(modifier = Modifier.padding(top = 16.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.director), fontWeight = FontWeight.Bold)
                    Text(text = movie.director, modifier = Modifier.padding(top = 8.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = stringResource(id = R.string.screenplay), fontWeight = FontWeight.Bold)
                    Text(text = movie.screenplay, modifier = Modifier.padding(top = 8.dp))
                }
            }
            Text(text = stringResource(id = R.string.cast), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp))
            Text(text = movie.cast, modifier = Modifier.padding(top = 8.dp, bottom = 16.dp))
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(true) {
        viewModel.dispatch(DetailsAction.OpenMovieDetailsAction(movieId.toInt()))
    }
}

private fun handleSideEffect(
    effect: DetailsEffect,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    action: () -> Unit
) {
    when (effect) {
        is DetailsEffect.DetailsErrorEffect -> {
            scope.launch {
                when (
                    scaffoldState.snackbarHostState.showSnackbar(
                        effect.message,
                        "Retry",
                        SnackbarDuration.Indefinite
                    )
                ) {
                    SnackbarResult.Dismissed -> {}
                    SnackbarResult.ActionPerformed -> {
                        action()
                    }
                }
            }
        }
    }
}
