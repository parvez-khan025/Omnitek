package com.example.omnitek.data.repository

import android.net.Uri
import com.example.omnitek.core.Nodes
import com.example.omnitek.data.models.Profile
import com.example.omnitek.data.models.toMap
import com.example.omnitek.data.service.ProfileService
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import jakarta.inject.Inject

class ProfileRepository @Inject constructor(
    private val db: FirebaseFirestore,
    private val storageRef: StorageReference
): ProfileService {
    override fun uploadProfileImage(profileImageUri: Uri): UploadTask {
        val storage = storageRef.child(Nodes.PROFILE).child("PRF-${System.currentTimeMillis()}")

        return storage.putFile(profileImageUri)

    }

    override fun updateUser(user: Profile): Task<Void> {
        return db.collection(Nodes.USER).document(user.userID).update(user.toMap())
    }

    override fun getUserByUserID(userID: String): Task<QuerySnapshot> {
        return db.collection(Nodes.USER).whereEqualTo(Nodes.USERID,userID).get()
    }
}