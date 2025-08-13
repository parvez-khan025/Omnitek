package com.example.omnitek.core


import android.webkit.PermissionRequest
import android.widget.EditText
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import java.security.Permission

fun EditText.extract(): String {
    return text.toString().trim()
}

fun Fragment.requestPermissions(
    request: ActivityResultLauncher<Array<String>>,
    permission: Array<String>
){
    request.launch(permission)
}

fun Fragment.allPermissionGranted(permission: Array<String>): Boolean {
    return permission.all {
        ContextCompat.checkSelfPermission(requireContext(), it) == android.content.pm.PackageManager.PERMISSION_GRANTED
    }
}
