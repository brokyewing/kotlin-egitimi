package com.example.a6odev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.a6odev.databinding.FragmentAnasayfaBinding

class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var filmlerAdapter: FilmlerAdapter
    private lateinit var filmlerListesi: ArrayList<Filmler>
    private lateinit var filteredList: ArrayList<Filmler>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)

        filmlerListesi = ArrayList()
        filteredList = ArrayList()

        // Initialize movie list with correct drawable names
        filmlerListesi.add(Filmler(1, "Atatürk 1", "ataturk1"))
        filmlerListesi.add(Filmler(2, "Atatürk 2", "ataturk2"))
        filmlerListesi.add(Filmler(3, "Yeşil Yol", "yesilyol"))
        filmlerListesi.add(Filmler(4, "Yüzüklerin Efendisi", "yuzuklerin_efendisi"))
        filmlerListesi.add(Filmler(5, "Yüzüklerin Efendisi: İki Kule", "yuzuklerin_efendisi_iki_kule"))
        filmlerListesi.add(Filmler(6, "Yüzüklerin Efendisi: Kralın Dönüşü", "yuzuklerin_efendisi_kralin_donus"))
        filmlerListesi.add(Filmler(7, "Nautilus", "nautilus"))
        filmlerListesi.add(Filmler(8, "Eşref Rüya", "esrefruya"))

        filteredList.addAll(filmlerListesi)

        filmlerAdapter = FilmlerAdapter(requireContext(), filteredList)
        binding.filimlerRv.adapter = filmlerAdapter
        binding.filimlerRv.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return binding.root
    }

    fun filterMovies(query: String?) {
        filteredList.clear()
        val searchText = query?.lowercase() ?: ""
        if (searchText.isNotEmpty()) {
            filmlerListesi.forEach {
                if (it.name.lowercase().contains(searchText)) {
                    filteredList.add(it)
                }
            }
        } else {
            filteredList.addAll(filmlerListesi)
        }
        filmlerAdapter.notifyDataSetChanged()
    }
}