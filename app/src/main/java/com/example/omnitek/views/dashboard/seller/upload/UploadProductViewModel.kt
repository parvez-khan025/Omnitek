package com.example.omnitek.views.dashboard.seller.upload

import android.net.Uri
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.omnitek.core.DataState
import com.example.omnitek.data.models.Product
import com.example.omnitek.data.repository.SellerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class UploadProductViewModel @Inject constructor(
    private val repo: SellerRepository

): ViewModel() {
    private val _productUploadResponse = MutableLiveData<DataState<String>>()

    val productUploadResponse: LiveData<DataState<String>> = _productUploadResponse



    fun productUpload(product: Product){

        _productUploadResponse.postValue(DataState.Loading())

        val imageUri: Uri = product.imagelink.toUri()

        repo.uploadProductImage(imageUri).addOnSuccessListener { snapshot->

            snapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { url->
                product.imagelink = url.toString()
                repo.uploadProduct(product).addOnSuccessListener {
                    _productUploadResponse.postValue(DataState.Success("Product Uploaded Successfully"))
                }.addOnFailureListener {
                    _productUploadResponse.postValue(DataState.Error("Product Uploaded fail!"))
                }

            }

        }.addOnFailureListener {
            _productUploadResponse.postValue(DataState.Error("Image Uploaded fail!"))
        }


    }
}
