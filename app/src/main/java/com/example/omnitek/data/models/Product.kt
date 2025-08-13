package com.example.omnitek.data.models

data class Product(
    var name: String = "",
    var description: String = "",
    var price: Double = 0.0,
    var quantity: Int = 0,
    var imagelink: String = "",
    var sellerId: String = "",
    var productId: String = ""
)
