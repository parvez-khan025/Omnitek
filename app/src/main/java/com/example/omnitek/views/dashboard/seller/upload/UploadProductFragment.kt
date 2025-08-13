package com.example.omnitek.views.dashboard.seller.upload

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.DataState
import com.example.omnitek.core.allPermissionGranted
import com.example.omnitek.core.extract
import com.example.omnitek.core.requestPermissions
import com.example.omnitek.data.models.Product
import com.example.omnitek.databinding.FragmentUploadProductBinding
import com.example.omnitek.views.dashboard.seller.SellerDashboard
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID


@AndroidEntryPoint
class UploadProductFragment : BaseFragment<FragmentUploadProductBinding>(
    FragmentUploadProductBinding::inflate
){
    private val product : Product by lazy (){
        Product()
    }

    private val viewModel: UploadProductViewModel by viewModels()
    override fun setListener() {

        permissionRequest = getPermissionsRequest()

        with(binding) {
            ivProduct.setOnClickListener {
                requestPermissions(permissionRequest, permissionList)
            }

            btnUploadProduct.setOnClickListener {
                val name = productName.extract()
                val description = productDescription.extract()
                val price = productPrice.extract()
                val quantity = productQuantity.extract()

                FirebaseAuth.getInstance().currentUser?.let {
                    product.apply {
                        this.productId = UUID.randomUUID().toString()
                        this.sellerId = it.uid
                        this.name = name
                        this.description = description
                        this.price = price.toDouble()
                        this.quantity = quantity.toInt()
                    }
                }


                uploadProduct(product)
                startActivity(Intent(requireContext(), SellerDashboard::class.java))
                requireActivity().finish()

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
        viewModel.productUpload(product)
    }

    override fun allObserver() {
        viewModel.productUploadResponse.observe(viewLifecycleOwner){
            when(it){
                is DataState.Error ->{
                    loading.dismiss()
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                }
                is DataState.Loading ->{
                    Toast.makeText(requireContext(), "Loading", Toast.LENGTH_LONG).show()
                }
                is DataState.Success ->{
                    loading.dismiss()
                    Toast.makeText(requireContext(), it.data, Toast.LENGTH_LONG).show()
                }
            }
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


    private lateinit var permissionRequest : ActivityResultLauncher<Array<String>>

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            if (resultCode == Activity.RESULT_OK) {

                val fileUri = data?.data!!
                binding.ivProduct.setImageURI(fileUri)
                product.imagelink = fileUri.toString()
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(requireContext(), ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}