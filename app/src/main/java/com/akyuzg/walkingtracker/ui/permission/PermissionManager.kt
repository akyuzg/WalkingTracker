package com.akyuzg.walkingtracker.ui.permission

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akyuzg.walkingtracker.BuildConfig
import com.akyuzg.walkingtracker.R
import java.lang.ref.WeakReference

class PermissionManager(
    _fragment: WeakReference<Fragment>
) {

    private val fragment: Fragment = _fragment.get()!!

    companion object {
        fun inject(fragment: Fragment) = PermissionManager(WeakReference(fragment))
    }

    private fun openAppSettingsDetailPage(){
        val intent = Intent()
        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        val uri = Uri.fromParts("package", BuildConfig.APPLICATION_ID, null)
        intent.data = uri
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        fragment.startActivity(intent)
    }

    private val permissionsResultCallback = fragment.registerForActivityResult(ActivityResultContracts.RequestPermission()){
        when (it) {
            true -> { Toast.makeText(fragment.requireContext(), getString(R.string.permission_granted_by_user), Toast.LENGTH_SHORT).show() }
            false -> {
                openAppSettingsDetailPage()
                Toast.makeText(fragment.requireContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun requestFineLocationPermission(){
        askForPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    fun requestBackgroundPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            askForPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }
    }

    @SuppressLint("ResourceType")
    private fun askForPermission(permission: String) {
        val rationaleRequired = fragment.shouldShowRequestPermissionRationale(permission)

        if(rationaleRequired){
            AlertDialog.Builder(fragment.requireContext())
                .setTitle(getString(R.string.warning))
                .setMessage(getString(R.string.important_message))
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok)) { _, _ ->
                    permissionsResultCallback.launch(permission)
                }
                .show()
        }else{
            val permissionGranted = ContextCompat.checkSelfPermission(fragment.requireContext(), permission)
            if (permissionGranted != PackageManager.PERMISSION_GRANTED) {
                permissionsResultCallback.launch(permission)
            }
        }
    }

    private fun getString(@StringRes resource:Int): String {
        return fragment.getString(resource)
    }
}