package com.piedrasyartesanias.cakeapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.piedrasyartesanias.cakeapp.databinding.ItemProductBinding
import com.piedrasyartesanias.cakeapp.listener.ListenerProduct
import com.piedrasyartesanias.cakeapp.models.ProductResponse
import com.piedrasyartesanias.cakeapp.utils.URLBASE


class ProductsAdapter(val listProducts: List<ProductResponse>, val listener: ListenerProduct): RecyclerView.Adapter<ProductsAdapter.MyViewHolder>() {

    private lateinit var binding: ItemProductBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = ItemProductBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(listProducts[position])
    }

    inner class MyViewHolder(private val itemProductBinding: ItemProductBinding, private val ListenerProduct: ListenerProduct):RecyclerView.ViewHolder(itemProductBinding.root) {

        fun bindItem(product: ProductResponse) {
            itemProductBinding.textViewItem.text = product.name
            val url = "$URLBASE${product.image}"
            product.bitmap?.let {
                itemProductBinding.imageViewItem.setImageBitmap(it)
            } ?: Glide.with(itemProductBinding.root.context).load(url).into(itemProductBinding.imageViewItem)
            itemProductBinding.root.setOnClickListener {
             ListenerProduct.onClickedProduct(product)
            }
        }
    }
}