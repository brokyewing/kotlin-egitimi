package com.example.odev7.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.odev7.data.entity.Yapilacak
import com.example.odev7.databinding.CardYapilacakBinding
import com.example.odev7.ui.fragment.AnasayfaFragmentDirections
import com.example.odev7.ui.viewmodel.AnasayfaViewModel
import com.google.android.material.snackbar.Snackbar

class YapilacakAdapter(
    private val mContext: Context,
    private val yapilacaklarListesi: List<Yapilacak>,
    private val viewModel: AnasayfaViewModel
) : RecyclerView.Adapter<YapilacakAdapter.CardTasarimTutucu>() {
    
    inner class CardTasarimTutucu(private val binding: CardYapilacakBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        fun bind(yapilacak: Yapilacak) {
            binding.textViewYapilacakAd.text = yapilacak.name
            
            binding.cardViewYapilacak.setOnClickListener {
                val action = AnasayfaFragmentDirections.actionAnasayfaToDetay(yapilacak)
                Navigation.findNavController(it).navigate(action)
            }
            
            binding.imageViewSil.setOnClickListener {
                Snackbar.make(it, "${yapilacak.name} silinsin mi?", Snackbar.LENGTH_LONG)
                    .setAction("EVET") {
                        viewModel.sil(yapilacak)
                    }.show()
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val binding = CardYapilacakBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }
    
    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        holder.bind(yapilacaklarListesi[position])
    }
    
    override fun getItemCount(): Int {
        return yapilacaklarListesi.size
    }
} 