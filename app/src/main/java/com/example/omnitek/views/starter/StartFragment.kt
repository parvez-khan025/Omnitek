package com.example.omnitek.views.starter

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.databinding.FragmentStartBinding
import com.example.omnitek.views.dashboard.seller.SellerDashboard
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlin.jvm.java

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setListener() {

        setAutoLogin()

        with(binding) {

            loginButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
        }

    }

    private fun setAutoLogin() {
        FirebaseAuth.getInstance().currentUser?.let {
            startActivity(Intent(requireContext(), SellerDashboard::class.java))
            requireActivity().finish()
        }
    }

    override fun allObserver() {

    }
}