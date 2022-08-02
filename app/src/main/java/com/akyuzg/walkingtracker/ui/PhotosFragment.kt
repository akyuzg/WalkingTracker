package com.akyuzg.walkingtracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akyuzg.walkingtracker.databinding.FragmentPhotosBinding
import com.akyuzg.walkingtracker.ui.PhotosFragment.ButtonStatus.START_TEXT
import com.akyuzg.walkingtracker.ui.PhotosFragment.ButtonStatus.STOP_TEXT
import com.akyuzg.walkingtracker.ui.model.PhotoItemView
import com.akyuzg.walkingtracker.ui.permission.PermissionManager
import kotlinx.coroutines.launch
import javax.inject.Inject

class PhotosFragment @Inject constructor() : Fragment() {

    private val viewModel: PhotosViewModel by viewModels({ requireActivity() })

    private lateinit var adapter: PhotoAdapter
    private lateinit var binding: FragmentPhotosBinding
    private lateinit var permissionManager: PermissionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        setButtonStatus()
        initClickListeners()

        permissionManager.requestFineLocationPermission()
        permissionManager.requestBackgroundPermission()

        lifecycleScope.launch {
            viewModel.fetchPhotos().collect { photoViewItems ->
                adapter.setItems(photoViewItems)
            }
        }
    }

    private fun initClickListeners() {
        binding.action.setOnClickListener {
            if (viewModel.isLocationServiceActive()) {
                viewModel.stopLocationUpdates()
            } else {
                viewModel.startLocationUpdates()
            }
        }
    }

    private fun setButtonStatus() {
        binding.action.text = if (viewModel.isLocationServiceActive()) STOP_TEXT else START_TEXT
        viewModel.subscriptionActive.observe(viewLifecycleOwner) {
            binding.action.text = if (it) STOP_TEXT else START_TEXT
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        permissionManager = PermissionManager.inject(this)
        binding = FragmentPhotosBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    private fun initAdapter() {
        val items = ArrayList<PhotoItemView>()
        binding.recyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        adapter = PhotoAdapter(items)
        binding.recyclerView.adapter = adapter
    }

    object ButtonStatus {
        const val START_TEXT = "START"
        const val STOP_TEXT = "STOP"
    }

}