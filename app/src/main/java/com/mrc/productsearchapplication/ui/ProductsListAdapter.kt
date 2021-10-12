package com.mrc.productsearchapplication.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mrc.productsearchapplication.R
import com.mrc.productsearchapplication.data.Product
import com.mrc.productsearchapplication.databinding.GridItemLayoutBinding
import com.mrc.productsearchapplication.databinding.ListItemLayoutBinding

class ProductsListAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Product, ProductsListAdapter.ProductViewHolder>(PRODUCT_COMPARATOR) {

    private val listItemType = 1
    private val gridItemType = 2
    var isSwitchView = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return if (viewType == listItemType) {
            val binding = ListItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
            ProductViewHolder(binding)
        } else {
            val binding = GridItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context), parent,
                false
            )
            ProductViewHolder(binding)
        }


    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isSwitchView) {
            listItemType
        } else {
            gridItemType
        }

    }

    fun toggleItemViewType(): Boolean {
        isSwitchView = !isSwitchView
        return isSwitchView
    }


    inner class ProductViewHolder(
        private val binding: ViewBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(product: Product) {
            val bind: ViewBinding
            if (isSwitchView) {
                bind = binding as ListItemLayoutBinding
                setListItemViews(bind, product, itemView)
            } else {
                bind = binding as GridItemLayoutBinding
                setGridItemViews(bind, product, itemView)
            }
        }

    }


    private fun setListItemViews(
        binding: ListItemLayoutBinding, product: Product,
        itemView: View
    ) {
        binding.apply {
            Glide.with(itemView)
                .load(product.images[0])
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.color.grey)
                .into(productImage)
            productNameText.text = product.name

            product.price.offerPriceDisplay?.let {
                currentPriceText.text = product.price.offerPriceDisplay
                strikePriceText.isVisible = true
                strikePriceText.paintFlags =
                    strikePriceText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val oldPrice = product.price.strikeThroughPriceDisplay ?: ""
                strikePriceText.text = oldPrice
                if (product.price.discount > 0) {
                    discountPercentageText.isVisible = true
                    discountPercentageText.text = String.format(
                        itemView.context.getString(R.string.discount),
                        product.price.discount
                    )
                } else {
                    discountPercentageText.isVisible = false
                }
            } ?: run {
                currentPriceText.text = product.price.priceDisplay
                strikePriceText.isVisible = false
                discountPercentageText.isVisible = false
            }

            locationText.text = product.location
            ratingBar.rating = product.review.rating.toFloat()
            ratingCountText.text = String.format(
                itemView.context.getString(R.string.rating_count),
                product.review.count
            )
            if (product.review.rating > 0) {
                ratingBar.isVisible = true
                ratingCountText.isVisible = true
            } else {
                ratingBar.isVisible = false
                ratingCountText.isVisible = false
            }
            binding.actionBtn.setOnClickListener {
                listener.onItemClick(product)
            }
        }
    }


    private fun setGridItemViews(
        binding: GridItemLayoutBinding, product: Product,
        itemView: View
    ) {
        binding.apply {
            Glide.with(itemView)
                .load(product.images[0])
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.color.grey)
                .into(productImage)
            productNameText.text = product.name

            product.price.offerPriceDisplay?.let {
                currentPriceText.text = product.price.offerPriceDisplay
                strikePriceText.isVisible = true
                strikePriceText.paintFlags =
                    strikePriceText.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                val oldPrice = product.price.strikeThroughPriceDisplay ?: ""
                strikePriceText.text = oldPrice
                if (product.price.discount > 0) {
                    discountPercentageText.isVisible = true
                    discountPercentageText.text = String.format(
                        itemView.context.getString(R.string.discount),
                        product.price.discount
                    )
                } else {
                    discountPercentageText.isVisible = false
                }
            } ?: run {
                currentPriceText.text = product.price.priceDisplay
                strikePriceText.isVisible = false
                discountPercentageText.isVisible = false
            }

            locationText.text = product.location
            ratingBar.rating = product.review.rating.toFloat()
            ratingCountText.text = String.format(
                itemView.context.getString(R.string.rating_count),
                product.review.count
            )
            if (product.review.rating > 0) {
                ratingBar.isVisible = true
                ratingCountText.isVisible = true
            } else {
                ratingBar.isVisible = false
                ratingCountText.isVisible = false
            }
            binding.actionBtn.setOnClickListener {
                listener.onItemClick(product)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(product: Product)
    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {

            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem

        }
    }


}
