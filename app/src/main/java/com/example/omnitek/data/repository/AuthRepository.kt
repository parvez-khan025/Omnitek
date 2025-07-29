package com.example.omnitek.data.repository

import com.example.omnitek.data.models.UserLogin
import com.example.omnitek.data.models.UserRegistration
import com.example.omnitek.data.service.AuthService
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthRepository @Inject constructor(

    private val jAuth: FirebaseAuth
) : AuthService  {

    override fun userRegistration(user: UserRegistration): Task<AuthResult> {

       return jAuth.createUserWithEmailAndPassword(user.email, user.password)

    }

    override fun userLogin(user: UserLogin): Task<AuthResult> {
        return jAuth.signInWithEmailAndPassword(user.email, user.password)
    }

}