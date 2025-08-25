package com.example.omnitek.views.dashboard.seller.product

import androidx.fragment.app.viewModels
import com.example.omnitek.base.BaseFragment
import com.example.omnitek.core.DataState
import com.example.omnitek.data.models.Product
import com.example.omnitek.databinding.FragmentMyProductBinding
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MyProductFragment :
    BaseFragment<FragmentMyProductBinding>(FragmentMyProductBinding::inflate) {

    private val viewModel: ProductViewModel by viewModels()

    override fun setListener() {
        FirebaseAuth.getInstance().currentUser?.let {
            viewModel.getProductByID(it.uid)

        }

    }

    override fun allObserver() {
        viewModel.productResponse.observe(viewLifecycleOwner) {

            when (it) {
                is DataState.Error -> {
                    loading.dismiss()
                }

                is DataState.Loading -> {
                    loading.show()
                }

                is DataState.Success -> {
                    loading.dismiss()
                    it.data?.let { it1 ->
                        setDataToRV(it1)
                    }
                }
            }
        }

    }

    private fun setDataToRV(it: List<Product>) {
        binding.rvSeller.adapter = SellerProductAdapter(it)
    }
}