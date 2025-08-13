package com.example.omnitek.views.dashboard.seller.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.omnitek.data.models.Product
import com.example.omnitek.databinding.ItemSellerProductBinding

class SellerProductAdapter(val productList : List<Product>) : RecyclerView.Adapter<SellerProductAdapter.ProductViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductViewHolder {

        return ProductViewHolder(ItemSellerProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    }

    override fun onBindViewHolder(
        holder: ProductViewHolder,
        position: Int
    ) {
        productList[position].let {
            holder.binding.apply {
                TVProductName.text = it.name
                TVProductDescription.text = it.description
                TVProductStock.text = "Stock : ${it.quantity}"
                TVProductPrice.text = "Price : ${it.price}"
                ivProduct.load(it.imagelink)
            }
        }


    }

    override fun getItemCount(): Int {

        return productList.size

    }

    class ProductViewHolder(val binding: ItemSellerProductBinding): RecyclerView.ViewHolder(binding.root)
}