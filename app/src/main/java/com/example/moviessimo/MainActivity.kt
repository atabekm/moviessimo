package com.example.moviessimo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.feature.list.presentation.ListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.main_container, ListFragment())
            .commit()
    }
}
