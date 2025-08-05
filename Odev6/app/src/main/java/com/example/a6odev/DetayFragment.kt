package com.example.a6odev

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.a6odev.databinding.FragmentDetayBinding

class DetayFragment : Fragment() {
    private lateinit var binding: FragmentDetayBinding
    private val args: DetayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetayBinding.inflate(inflater, container, false)

        val film = args.filmNesnesi

        // Set film details
        binding.textViewFilmAdi.text = film.name
        binding.textViewFilmAciklama.text = "Prime üyeliği ile hemen izleyebilirsiniz."

        // Set film image
        val resourceId = requireContext().resources.getIdentifier(
            film.resim,
            "drawable",
            requireContext().packageName
        )
        if (resourceId != 0) {
            binding.imageViewFilm.setImageResource(resourceId)
        }

        // Handle toolbar navigation
        binding.detailToolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

        // Handle button clicks
        binding.buttonOynat.setOnClickListener {
            // Handle play button click
        }

        binding.buttonIndir.setOnClickListener {
            // Handle download button click
        }

        return binding.root
    }
}