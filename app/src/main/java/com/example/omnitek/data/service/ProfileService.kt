package com.example.omnitek.data.service

import android.net.Uri
import com.example.omnitek.data.models.Profile
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.UploadTask

interface ProfileService {
    fun uploadProfileImage(profileImageUri: Uri): UploadTask
    fun updateUser(user: Profile): Task<Void>
    fun getUserByUserID(userID: String): Task<QuerySnapshot>
}