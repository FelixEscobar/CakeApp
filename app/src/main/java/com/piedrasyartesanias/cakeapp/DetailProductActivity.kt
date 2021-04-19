package com.piedrasyartesanias.cakeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.piedrasyartesanias.cakeapp.databinding.ActivityDetailProductBinding
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import com.piedrasyartesanias.cakeapp.utils.URL
import kotlinx.android.synthetic.main.fragment_sign_up.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductActivity : AppCompatActivity() {
    companion object {
        const val PRODUCT_DETAILS = "PRODUCT_DETAILS"
    }

    private lateinit var binding: ActivityDetailProductBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailProductBinding.bind(layoutInflater)
        setContentView(binding.root)

        val product: ProductResponse = intent.getParcelableExtra<ProductResponse>(PRODUCT_DETAILS)

        CoroutineScope (Dispatchers.Main).launch {
            this: CoroulineScope
            try {
                val url = "$URL${product.image}"
                Glide.with(
                    this(DetailProductActivity).load(
                        url.into(binding.imageViewItem)
                                binding . productNameTextViewItem . text = product . editTextName
                                binding.detailValueTextView.text = product.description
                                binding . priceValueTextView . text = product . price . toString () + " $ "
                                binding . deliveryTimeValueTextView . text = product . deliveryTime . toString () + "min."
            }
        }

        setContentView(R.layout.activity_detail_product)
        val response: ProductResponse = intent.extras!!.get("product") as ProductResponse
    }
}