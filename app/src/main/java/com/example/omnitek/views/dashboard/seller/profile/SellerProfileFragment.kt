package com.example.omnitek.views.dashboard.seller.profile

import android.Manifest
import android.app.Activity
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.DataState
import com.example.omnitek.core.allPermissionGranted
import com.example.omnitek.core.extract
import com.example.omnitek.core.requestPermissions
import com.example.omnitek.data.models.Profile
import com.example.omnitek.databinding.FragmentSellerProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SellerProfileFragment : BaseFragment<FragmentSellerProfileBinding>(
    FragmentSellerProfileBinding::inflate
) {
    private val sellerProfile: Profile? = null
    private val viewModel: SellerProfileViewModel by viewModels()

    private var hasLocalImageUrl: Boolean = false


    override fun setListener() {

        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getUserByUserID(it.uid)
        }

        permissionsRequest = getPermissionsRequest()

        with(binding) {
            ivProfile.setOnClickListener {
                requestPermissions(permissionsRequest, permissionList)
            }
            btnUpdate.setOnClickListener {
                loading.show()
                val name = etName.extract()
                val email = etEmail.extract()

                sellerProfile.apply {
                    this?.name = name
                    this?.email = email
                }

                updateProfile(sellerProfile)

            }
        }


    }

    private fun updateProfile(sellerProfile: Profile?) {
        sellerProfile?.let {
            viewModel.updateProfile(it, hasLocalImageUrl)
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

    override fun allObserver() {
        viewModel.logedInUserResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error ->{
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.profileUpdateResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error ->{
                    loading.dismiss()
                }
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    sellerProfileData(sellerProfile)
                    loading.dismiss()
                }
            }
        }
    }

    private fun sellerProfileData(sellerProfile: Profile?) {

        hasLocalImageUrl = sellerProfile?.userImage.isNullOrBlank()

        binding.apply {
            etName.setText(sellerProfile?.name)
            etEmail.setText(sellerProfile?.email)
            ivProfile.load(sellerProfile?.userImage)
        }
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

    private lateinit var permissionsRequest: ActivityResultLauncher<Array<String>>


    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                binding.ivProfile.setImageURI(fileUri)
                sellerProfile?.userImage = fileUri.toString()
                hasLocalImageUrl = true

            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }

}