package com.brokyfood.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brokyfood.app.R
import com.brokyfood.app.data.model.Food
import com.brokyfood.app.databinding.ItemFoodBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class FoodAdapter(
    private val onFoodClick: (Food) -> Unit
) : ListAdapter<Food, FoodAdapter.FoodViewHolder>(FoodDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FoodViewHolder(
        private val binding: ItemFoodBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onFoodClick(getItem(position))
                }
            }
        }

        fun bind(food: Food) {
            binding.apply {
                foodName.text = food.name
                foodPrice.text = root.context.getString(R.string.price_format, food.price)
                
                Glide.with(foodImage)
                    .load("http://kasimadalan.pe.hu/yemekler/resimler/${food.imageUrl}")
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.food_placeholder)
                    .error(R.drawable.food_error)
                    .into(foodImage)
            }
        }
    }

    object FoodDiffCallback : DiffUtil.ItemCallback<Food>() {
        override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
            return oldItem == newItem
        }
    }
} 