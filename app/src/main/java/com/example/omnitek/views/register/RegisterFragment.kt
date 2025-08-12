package com.example.omnitek.views.register

import android.content.Intent
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.omnitek.R
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.DataState
import com.example.omnitek.data.models.UserRegistration
import com.example.omnitek.databinding.FragmentRegisterBinding
import com.example.omnitek.isEmpty
import com.example.omnitek.views.dashboard.seller.SellerDashboard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    private val viewModel: RegistrationViewModel by viewModels()


    override fun setListener() {
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

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etNum.isEmpty() && !etPass.isEmpty() && !toggleButton.isEmpty()) {

                    val user = UserRegistration(
                        name = etName.text.toString(),
                        number = etNum.text.toString(),
                        email = etEmail.text.toString(),
                        password = etPass.text.toString(),
                        userType = "Seller",
                        userID = ""
                    )
                    viewModel.userRegistration(user)

                    etName.text?.clear()
                    etEmail.text?.clear()
                    etNum.text?.clear()
                    etPass.text?.clear()
                }

            }
        }
        }

    override fun allObserver() {
        viewModel.registrationResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    loading.show()
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(requireContext(), SellerDashboard::class.java))
                    requireActivity().finish()                }
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }    }

    }