package com.example.odev7.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.odev7.databinding.FragmentKayitBinding
import com.example.odev7.ui.viewmodel.KayitViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class KayitFragment : Fragment() {
    private lateinit var binding: FragmentKayitBinding
    private lateinit var viewModel: KayitViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentKayitBinding.inflate(inflater, container, false)
        
        binding.buttonKaydet.setOnClickListener {
            val yapilacakAd = binding.editTextYapilacak.text.toString()
            viewModel.kaydet(yapilacakAd)
            Navigation.findNavController(it).navigate(KayitFragmentDirections.actionKayitToAnasayfa())
        }
        
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: KayitViewModel by viewModels()
        viewModel = tempViewModel
    }
} 