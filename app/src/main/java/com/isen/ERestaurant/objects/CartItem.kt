package com.isen.ERestaurant.objects

import java.io.Serializable

data class CartItem (
    val plat : Item,
    var quantité : Int
        ): Serializable
