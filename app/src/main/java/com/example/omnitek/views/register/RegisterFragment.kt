package com.example.omnitek.views.register

import android.content.Intent
import android.widget.Toast
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
    private var selectedUserType: String = ""   // keep track

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

                val userType = when (toggleButton.checkedButtonId) {
                    R.id.btn_customer -> "Customer"
                    R.id.btn_seller -> "Seller"
                    else -> ""
                }

                if (!etName.isEmpty() && !etEmail.isEmpty() && !etNum.isEmpty() && !etPass.isEmpty() && userType.isNotEmpty()) {

                    selectedUserType = userType   // ✅ save it

                    val user = UserRegistration(
                        name = etName.text.toString(),
                        number = etNum.text.toString(),
                        email = etEmail.text.toString(),
                        password = etPass.text.toString(),
                        userType = userType,
                        userID = ""
                    )
                    viewModel.userRegistration(user)

                    etName.text?.clear()
                    etEmail.text?.clear()
                    etNum.text?.clear()
                    etPass.text?.clear()
                    toggleButton.clearChecked()
                } else {
                    Toast.makeText(requireContext(), "Please fill all fields and select user type", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun allObserver() {
        viewModel.registrationResponse.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Loading -> {
                    loading.show()
                }
                is DataState.Success -> {
                    loading.dismiss()
                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()


                    when (selectedUserType) {
                        "Seller" -> startActivity(Intent(requireContext(), SellerDashboard::class.java))
//                        "Customer" -> startActivity(Intent(requireContext(), CustomerDashboard::class.java))
                    }
                    requireActivity().finish()
                }
                is DataState.Error -> {
                    loading.dismiss()
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
