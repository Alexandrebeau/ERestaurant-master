package com.isen.ERestaurant.objects

import java.io.Serializable

data class ItemModel(
    var name_fr: String,
    var items: ArrayList<Item>,

    ): Serializable
