package com.example.odev7.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.odev7.databinding.FragmentAnasayfaBinding
import com.example.odev7.ui.adapter.YapilacakAdapter
import com.example.odev7.ui.viewmodel.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
    private lateinit var binding: FragmentAnasayfaBinding
    private lateinit var viewModel: AnasayfaViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false)
        
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        
        viewModel.yapilacaklarListesi.observe(viewLifecycleOwner) {
            val adapter = YapilacakAdapter(requireContext(), it, viewModel)
            binding.recyclerView.adapter = adapter
        }
        
        binding.fab.setOnClickListener {
            val action = AnasayfaFragmentDirections.actionAnasayfaToKayit()
            Navigation.findNavController(it).navigate(action)
        }
        
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.ara(query)
                return true
            }
            
            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.ara(newText)
                return true
            }
        })
        
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: AnasayfaViewModel by viewModels()
        viewModel = tempViewModel
    }
    
    override fun onResume() {
        super.onResume()
        viewModel.yapilacaklariYukle()
    }
} 