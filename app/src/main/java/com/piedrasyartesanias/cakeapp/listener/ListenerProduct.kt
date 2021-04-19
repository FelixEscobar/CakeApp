package com.piedrasyartesanias.cakeapp.listener

import com.piedrasyartesanias.cakeapp.models.ProductResponse

interface ListenerProduct {

    fun onClickedProduct(product: ProductResponse)

}