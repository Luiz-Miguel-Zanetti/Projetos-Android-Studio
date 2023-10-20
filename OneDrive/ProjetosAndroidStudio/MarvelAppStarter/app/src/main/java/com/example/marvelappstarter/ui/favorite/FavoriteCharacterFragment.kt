package com.example.marvelappstarter.ui.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.marvelappstarter.databinding.FragmentFavoriteCharacterBinding
import com.example.marvelappstarter.databinding.FragmentSearchCharacterBinding
import com.example.marvelappstarter.ui.base.BaseFragment
import com.example.marvelappstarter.ui.search.SearchCharacterViewlModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteCharacterFragment : BaseFragment<FragmentFavoriteCharacterBinding, FavoriteCharacterViewModel>() {


    override val viewModel: FavoriteCharacterViewModel by viewModels()

    override fun getViewBindind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteCharacterBinding = FragmentFavoriteCharacterBinding.inflate(inflater, container, false)
}