package com.example.omnitek.views.login

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.databinding.FragmentLoginBinding
import com.example.omnitek.isEmpty

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        setOnClickListener()

        return binding.root

    }

    private fun setOnClickListener() {
        with(binding) {
            registerTV.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
            }
            loginBtn.setOnClickListener {
                etEmail.isEmpty()
                etPass.isEmpty()

                if (!etEmail.isEmpty() && !etPass.isEmpty()){
                    Toast.makeText(context, "Login Success", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}