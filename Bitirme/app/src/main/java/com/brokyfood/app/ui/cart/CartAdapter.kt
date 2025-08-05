package com.brokyfood.app.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brokyfood.app.R
import com.brokyfood.app.data.model.CartFood
import com.brokyfood.app.databinding.ItemCartBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CartAdapter(
    private val onRemoveClick: (CartFood) -> Unit,
    private val onQuantityChanged: (CartFood, Int) -> Unit
) : ListAdapter<CartFood, CartAdapter.CartViewHolder>(CartDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class CartViewHolder(
        private val binding: ItemCartBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.removeButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onRemoveClick(getItem(position))
                }
            }

            binding.decreaseButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item.quantity > 1) {
                        onQuantityChanged(item, item.quantity - 1)
                    }
                }
            }

            binding.increaseButton.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    onQuantityChanged(item, item.quantity + 1)
                }
            }
        }

        fun bind(cartFood: CartFood) {
            binding.apply {
                foodName.text = cartFood.name
                foodPrice.text = root.context.getString(R.string.price_format, cartFood.price)
                quantity.text = root.context.getString(R.string.items_count, cartFood.quantity)
                totalPrice.text = root.context.getString(R.string.price_format, cartFood.price * cartFood.quantity)
                
                Glide.with(foodImage)
                    .load("http://kasimadalan.pe.hu/yemekler/resimler/${cartFood.imageUrl}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.food_placeholder)
                    .error(R.drawable.food_error)
                    .into(foodImage)

                decreaseButton.isEnabled = cartFood.quantity > 1
                increaseButton.isEnabled = true
            }
        }
    }

    object CartDiffCallback : DiffUtil.ItemCallback<CartFood>() {
        override fun areItemsTheSame(oldItem: CartFood, newItem: CartFood): Boolean {
            return oldItem.cartId == newItem.cartId
        }

        override fun areContentsTheSame(oldItem: CartFood, newItem: CartFood): Boolean {
            return oldItem == newItem
        }
    }
} 