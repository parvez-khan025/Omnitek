package com.example.omnitek.views.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.databinding.FragmentRegisterBinding
import com.example.omnitek.isEmpty

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        setOnClickListener()

        return binding.root
    }

    private fun setOnClickListener() {
        with(binding) {
            loginTV.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
            registerBtn.setOnClickListener {
                etName.isEmpty()
                etEmail.isEmpty()
                etNum.isEmpty()
                etPass.isEmpty()
                toggleButton.isEmpty()

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etNum.isEmpty() && !etPass.isEmpty() && !toggleButton.isEmpty()){
                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}