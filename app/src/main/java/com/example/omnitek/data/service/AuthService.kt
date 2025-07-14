package com.example.omnitek.data.service

import com.example.omnitek.data.models.UserRegistration
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult


interface AuthService {
    fun userRegistration(user:UserRegistration): Task<AuthResult>
    fun Login()
}