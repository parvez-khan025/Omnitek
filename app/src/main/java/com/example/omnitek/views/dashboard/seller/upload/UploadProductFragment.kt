package com.example.omnitek.views.dashboard.seller.upload

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.allPermissionGranted
import com.example.omnitek.core.extract
import com.example.omnitek.core.requestPermissions
import com.example.omnitek.data.models.Product
import com.example.omnitek.databinding.FragmentUploadProductBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UploadProductFragment : BaseFragment<FragmentUploadProductBinding>(
    FragmentUploadProductBinding::inflate
){

    override fun setListener() {

        permissionRequest = getPermissionsRequest()

        with(binding) {
            ivProduct.setOnClickListener {
                requestPermissions(permissionRequest, permissionList)
            }

            btnUploadProduct.setOnClickListener {

                uploadProduct(product)

            }
        }

    }

    private fun getPermissionsRequest(): ActivityResultLauncher<Array<String>> {

        return registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){

            if (allPermissionGranted(permissionList)){
                ImagePicker.with(this)
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .createIntent { intent ->
                        startForProfileImageResult.launch(intent)
                    }
            }else{
                Toast.makeText(requireContext(), "Permission Denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun uploadProduct(product: Product) {
    }

    override fun allObserver() {
    }


    companion object{
        private val permissionList = if (Build.VERSION.SDK_INT >=33){
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        }else{
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }


    private lateinit var permissionRequest : ActivityResultLauncher<Array<String>>

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {

                val fileUri = data?.data!!
                binding.ivProduct.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}