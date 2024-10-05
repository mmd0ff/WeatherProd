package com.example.weatherprod.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.navGraphViewModels
import com.example.weatherprod.Adapter.FavorAdapter
import com.example.weatherprod.MyViewModel
import com.example.weatherprod.R
import com.example.weatherprod.databinding.FragmentFavorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Favor : Fragment() {
    private var binding: FragmentFavorBinding? = null
    private val viewModel by navGraphViewModels<MyViewModel>(R.id.nav_graph)
    private var adatper = FavorAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavorBinding.inflate(layoutInflater,container,false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerView?.adapter = adatper
    }


    }
