package com.akyuzg.walkingtracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.akyuzg.walkingtracker.R
import com.akyuzg.walkingtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var photosFragment: PhotosFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, photosFragment)
                .commit()
        }
    }

}