package com.example.feature.list.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.feature.list.R
import com.example.feature.list.domain.model.MoviePoster
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

private const val COLUMNS_SIZE = 3

@Composable
fun MovieListScreen(
    navigateToDetails: (Int) -> Unit
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_name)) }
            )
        },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues),
            color = MaterialTheme.colors.background
        ) {
            MovieList(
                viewModel = getViewModel(),
                scope = scope,
                scaffoldState = scaffoldState,
                navigateToDetails = navigateToDetails
            )
        }
    }
}

@Composable
private fun MovieList(
    viewModel: ListViewModel,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    navigateToDetails: (Int) -> Unit
) {
    val state by viewModel.collectAsState()
    viewModel.collectSideEffect { handleSideEffect(it, scope, scaffoldState) { viewModel.dispatch(ListAction.LoadMoviesAction) } }

    LazyVerticalGrid(
        columns = GridCells.Fixed(COLUMNS_SIZE),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(state.moviePosters) { movie ->
            MovieRow(movie, navigateToDetails)
        }
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    LaunchedEffect(true) {
        viewModel.dispatch(ListAction.LoadMoviesAction)
    }
}

private fun handleSideEffect(
    effect: ListEffect,
    scope: CoroutineScope,
    scaffoldState: ScaffoldState,
    action: () -> Unit
) {
    when (effect) {
        is ListEffect.ListErrorEffect -> {
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

@Composable
private fun MovieRow(moviePoster: MoviePoster, navigateToDetails: (Int) -> Unit) {
    AsyncImage(
        model = moviePoster.posterImageUrl,
        contentDescription = moviePoster.title,
        modifier = Modifier.clickable { navigateToDetails(moviePoster.id) },
        contentScale = ContentScale.FillWidth
    )
}
