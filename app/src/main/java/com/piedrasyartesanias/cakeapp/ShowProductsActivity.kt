package com.piedrasyartesanias.cakeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.piedrasyartesanias.cakeapp.databinding.ActivityShowProductsBinding
import com.piedrasyartesanias.cakeapp.listener.ListenerProduct
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class ShowProductsActivity : AppCompatActivity(), ListenerProduct {

    private lateinit var binding: ActivityShowProductsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickedAllProducts()
        clickedDrinks()

        private fun clickedAllProducts() {
            binding.allProducts.setOnClickListener {
                getProductsByCategory(category: "allProducts")
            }
        }


        private fun clickedDrinks() {
            binding.drinks.setOnClickListener {
                getProductsByCategory(category: "drinks")
            }
        }
    }


        private fun getProductsByCategory(category: String) {
            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val products = withContext(Dispatchers.Default) {
                        val cakeRepository = CakeRepository()
                        when (category) {
                            "allProducts" -> cakeRepository.getAllProducts()
                            "drinks" -> cakeRepository.getDrinks(1)
                        }
                    }
                    binding.recyclerViewProducts.layoutManager =
                        GridLayoutManager(this@ShowProductsActivity, 2)
                    binding.recyclerViewProducts.adapter =
                        ProductsAdapter(
                            products as List<ProductResponse>,
                            this@ShowProductsActivity
                        )
                } catch (e: Exception) {
                    Toast.makeText(this@ShowProductsActivity, "${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        override fun onClickedProduct(product: ProductResponse) {
            startActivity(Intent(this, DetailProductActivity::class.java).apply {
                putExtra(DetailProductActivity.PRODUCT_DETAILS, product)
            })
        }
 }