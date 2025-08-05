package com.example.a6odev

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.a6odev.databinding.CardTasarimBinding

class FilmlerAdapter(private val mContext: Context, private val filmlerListesi: List<Filmler>) 
    : RecyclerView.Adapter<FilmlerAdapter.CardTasarimTutucu>() {

    inner class CardTasarimTutucu(val tasarim: CardTasarimBinding) : RecyclerView.ViewHolder(tasarim.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardTasarimTutucu {
        val binding = CardTasarimBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return CardTasarimTutucu(binding)
    }

    override fun onBindViewHolder(holder: CardTasarimTutucu, position: Int) {
        val film = filmlerListesi[position]
        val binding = holder.tasarim

        // Set movie title
        binding.textViewFilmAdi.text = film.name

        // Set movie image
        val resourceId = mContext.resources.getIdentifier(film.resim, "drawable", mContext.packageName)
        if (resourceId != 0) {
            binding.imageViewAtaturk1.setImageResource(resourceId)
        }

        binding.cardViewFilm.setOnClickListener {
            val gecis = AnasayfaFragmentDirections.detayGecis(film)
            Navigation.findNavController(it).navigate(gecis)
        }
    }

    override fun getItemCount(): Int = filmlerListesi.size
}