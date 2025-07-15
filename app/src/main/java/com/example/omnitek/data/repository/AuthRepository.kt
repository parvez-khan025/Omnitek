package com.example.omnitek.data.repository

import com.example.omnitek.data.models.UserRegistration
import com.example.omnitek.data.service.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class AuthRepository: AuthService  {

    private val jAuth = FirebaseAuth.getInstance()

    override fun userRegistration(user: UserRegistration): Task<AuthResult> {

       return jAuth.createUserWithEmailAndPassword(user.email, user.password)

    }

    override fun Login(email: String, password: String): Task<AuthResult> {

        return jAuth.signInWithEmailAndPassword(email, password)
    }
}