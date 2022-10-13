package com.example.moviessimo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.feature.details.presentation.MovieDetailsScreen
import com.example.feature.list.presentation.MovieListScreen
import com.example.moviessimo.ui.theme.MoviessimoTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviessimoApp()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MoviessimoApp()
}

@Composable
private fun MoviessimoApp() {
    val navController = rememberNavController()

    MoviessimoTheme {
        NavHost(navController = navController, startDestination = Screen.MovieList.route) {
            composable(Screen.MovieList.route) {
                MovieListScreen { movieId ->
                    navController.navigate(Screen.MovieDetails.createRoute(movieId))
                }
            }
            composable(
                Screen.MovieDetails.route,
                arguments = listOf(navArgument("movie_id") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movie_id")
                MovieDetailsScreen(movieId) {
                    navController.navigateUp()
                }
            }
        }
    }
}
