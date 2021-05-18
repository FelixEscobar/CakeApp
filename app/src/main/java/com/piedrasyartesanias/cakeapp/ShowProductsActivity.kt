package com.piedrasyartesanias.cakeapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.piedrasyartesanias.cakeapp.databinding.ActivityShowProductsBinding
import com.piedrasyartesanias.cakeapp.listener.ListenerProduct
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import com.piedrasyartesanias.cakeapp.persistence.LocalCakeRepository
import com.ethanhua.skeleton.Skeleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.ConnectException

class ShowProductsActivity : AppCompatActivity(), ListenerProduct {

    private lateinit var binding: ActivityShowProductsBinding
    private lateinit var localRepository: LocalCakeRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShowProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickedAllProducts()
        clickedDrinks()
        clickedAllCakes()
    }

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


    private fun clickedAllCakes() {
        binding.cakes.setOnClickListener {
            getProductsByCategory(category: "cakes")
        }
    }

    private fun getProducts(typeOfConnection: Any, category: String) : List<ProductResponse> {
        return when (category) {
            "allProducts" -> typeOfConnection.getAllProducts()
            "drinks" -> typeOfConnection.getProductsByCategory(1)
            "cakes" -> typeOfConnection.getProductsByCategory(2)
            else -> throw Exception("Error!!!")
        }
    }

    private fun getProductsByCategory(category: String) {

        var products : List<ProductResponse>


        binding.recyclerViewProducts.layoutManager =
            GridLayoutManager(this@ShowProductsActivity, 2)
        showSkeleton()
        localRepository = LocalCakeRepository(this@ShowProductsActivity)


        CoroutineScope(Dispatchers.Main).launch {
            try {
                   products = withContext(Dispatchers.Default) {
                       val cakeRepository = CakeRepository(this@ShowProductsActivity)
                       val internalProducts = when (category) {
                           "allProducts" -> cakeRepository.getAllProducts()
                           "drinks" -> cakeRepository.getByCategory(1)
                           "cakes" -> cakeRepository.getByCategory(2)
                           else -> throw Exception("Error!!!")
                       }
                       localRepository.insertProducts(internalProducts)
                       internalProducts
                   }
                    delay(3000)// solo para ambitos de que sepan que si se ve,
                    // como el servicio puede ser rápido no se alcanza a ver la transición,
                    // luego de que verifiquen borran o comentan esta línea
                    binding.recyclerViewProducts.adapter =
                        ProductsAdapter(products, this@ShowProductsActivity)

            } catch (e: Exception) {
                if (e is ConnectException) {
                    val productsResponse = withContext(Dispatchers.Default) {
                        localRepository.getAllProducts()
                    }
                    binding.recyclerViewProducts.adapter =
                        ProductsAdapter(productsResponse, this@ShowProductsActivity)
                } else {
                        Toast.makeText(this@ShowProductsActivity, "${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }


    override fun onClickedProduct(product: ProductResponse) {
        startActivity(Intent(this, DetailProductActivity::class.java).apply {
            putExtra(DetailProductActivity.PRODUCT_DETAILS, product)
        })
    }

    private fun showSkeleton() {
        Skeleton.bind(binding.recyclerViewProducts)
            .shimmer(true)
            .angle(20)
            .frozen(false)
            .duration(1000)
            .count(6)
            .color(R.color.white_opaque)
            .load(R.layout.skeleton_item_product)
            .show()
    }

   }

