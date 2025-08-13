package com.example.omnitek.data.repository

import android.net.Uri
import com.example.omnitek.core.Nodes
import com.example.omnitek.data.models.Product
import com.example.omnitek.data.service.SellerService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import javax.inject.Inject

class SellerRepository @Inject constructor(
    private val db : FirebaseFirestore,
    private val storageRef : StorageReference
): SellerService {
    override fun uploadProductImage(productImageUri: Uri): UploadTask {

        val storage = storageRef.child(Nodes.PRODUCT).child("PRD-${System.currentTimeMillis()}")

        return storage.putFile(productImageUri)
    }

    override fun uploadProduct(product: Product): Task<Void> {
        return db.collection(Nodes.PRODUCT).document(product.productId).set(product)
    }

    override fun getAllProductByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.PRODUCT).whereEqualTo(Nodes.SELLERID,userID).get()
    }
}