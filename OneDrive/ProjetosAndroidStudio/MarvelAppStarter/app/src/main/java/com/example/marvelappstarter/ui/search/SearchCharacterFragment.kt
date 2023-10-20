package com.example.marvelappstarter.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.marvelappstarter.databinding.FragmentSearchCharacterBinding
import com.example.marvelappstarter.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchCharacterFragment : BaseFragment<FragmentSearchCharacterBinding, SearchCharacterViewlModel>() {


    override val viewModel: SearchCharacterViewlModel by viewModels()

    override fun getViewBindind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchCharacterBinding = FragmentSearchCharacterBinding.inflate(inflater, container, false)
}