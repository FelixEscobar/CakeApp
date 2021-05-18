package com.piedrasyartesanias.cakeapp.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.piedrasyartesanias.cakeapp.persistence.entities.ProductEntity
import com.piedrasyartesanias.cakeapp.utils.URLBASE
import kotlinx.android.parcel.Parcelize
import java.net.URL

@Parcelize
data class ProductResponse(
    @SerializedName("_id")
    val id: String,
    val image: String,
    val description: String,
    val price: Int,
    val score: Int,
    @SerializedName("delivery_time")
    val deliveryTime: Int,
    val category: Int,
    val name: String,
    val bitmap: Bitmap? = null
) : Parcelable
{
    fun toProductEntity() = ProductEntity(id, image, description, price, score, deliveryTime, category, name, urlToBitmap("$URLBASE${image}"))

    private fun urlToBitmap(url:String): Bitmap {
        val openStream = URL(url).openStream()
        return BitmapFactory.decodeStream(openStream)
    }
}

