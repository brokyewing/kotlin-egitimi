package com.example.odev7.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.odev7.databinding.FragmentDetayBinding
import com.example.odev7.ui.viewmodel.DetayViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetayFragment : Fragment() {
    private lateinit var binding: FragmentDetayBinding
    private lateinit var viewModel: DetayViewModel
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetayBinding.inflate(inflater, container, false)
        
        val bundle: DetayFragmentArgs by navArgs()
        val yapilacak = bundle.yapilacak
        
        binding.editTextYapilacak.setText(yapilacak.name)
        
        binding.buttonGuncelle.setOnClickListener {
            val yapilacakAd = binding.editTextYapilacak.text.toString()
            yapilacak.name = yapilacakAd
            viewModel.guncelle(yapilacak)
            Navigation.findNavController(it).navigate(DetayFragmentDirections.actionDetayToAnasayfa())
        }
        
        return binding.root
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel: DetayViewModel by viewModels()
        viewModel = tempViewModel
    }
} 