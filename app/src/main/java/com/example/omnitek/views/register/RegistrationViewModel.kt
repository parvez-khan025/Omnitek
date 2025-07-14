package com.example.omnitek.views.register

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.omnitek.data.models.UserRegistration
import com.example.omnitek.data.repository.AuthRepository

class RegistrationViewModel: ViewModel() {

    fun userRegistration(user: UserRegistration) {

        val authService = AuthRepository()

        authService.userRegistration(user).addOnSuccessListener {
            Log.d("TAG", "userRegistration: Success")

        }.addOnFailureListener { error->
            Log.d("TAG", "userRegistration: ${error.message}")

        }

    }
}