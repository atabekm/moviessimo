package com.example.moviessimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.moviessimo.navigation.Navigator
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    val navigator: Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(findNavController(R.id.navHostFragment))
    }

    override fun onPause() {
        navigator.unbind()
        super.onPause()
    }
}
