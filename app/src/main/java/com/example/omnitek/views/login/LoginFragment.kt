package com.example.omnitek.views.login

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.data.repository.AuthRepository
import com.example.omnitek.databinding.FragmentLoginBinding
import com.example.omnitek.isEmpty

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    override fun setListener() {
        with(binding) {
            registerTV.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
            }

            loginBtn.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPass.text.toString().trim()

                val isEmailEmpty = etEmail.isEmpty()
                val isPasswordEmpty = etPass.isEmpty()

                if (!isEmailEmpty && !isPasswordEmpty) {
                    val authRepo = AuthRepository()
                    authRepo.Login(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
                        } else {
                            Toast.makeText(context, "Login Failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                        etEmail.text?.clear()
                        etPass.text?.clear()
                    }
                }
            }
        }
    }
    override fun allObserver() {
    }


}