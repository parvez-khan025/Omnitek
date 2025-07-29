package com.example.omnitek.views.starter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.databinding.FragmentStartBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StartFragment : BaseFragment<FragmentStartBinding>(FragmentStartBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun setListener() {
        with(binding) {

            loginButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_loginFragment)
            }

            registerButton.setOnClickListener {
                findNavController().navigate(R.id.action_startFragment_to_registerFragment)
            }
        }

    }

    override fun allObserver() {

    }
}