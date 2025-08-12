package com.example.omnitek.views.login

import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.DataState
import com.example.omnitek.data.models.UserLogin
import com.example.omnitek.data.repository.AuthRepository
import com.example.omnitek.databinding.FragmentLoginBinding
import com.example.omnitek.isEmpty
import com.example.omnitek.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel: LoginViewModel by viewModels()

    override fun setListener() {
        with(binding) {
            registerTV.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
            }

            loginBtn.setOnClickListener {
                etEmail.isEmpty()
                etPass.isEmpty()

                if (!etEmail.isEmpty() && !etPass.isEmpty()) {

                    val user = UserLogin(etEmail.text.toString(), etPass.text.toString())
                    viewModel.userLogin(user)

                    loading.show()
                    etEmail.text?.clear()
                    etPass.text?.clear()
                }
            }
        }
    }

    override fun allObserver() {
        viewModel.loginResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    loading.show()
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }

                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), SellerDashboard::class.java))
                    requireActivity().finish()                }

                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


