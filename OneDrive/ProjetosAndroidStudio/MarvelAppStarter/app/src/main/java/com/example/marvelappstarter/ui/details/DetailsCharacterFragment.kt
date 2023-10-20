package com.example.marvelappstarter.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.marvelappstarter.databinding.FragmentDetailsCharacterBinding
import com.example.marvelappstarter.databinding.FragmentSearchCharacterBinding
import com.example.marvelappstarter.ui.base.BaseFragment
import com.example.marvelappstarter.ui.search.SearchCharacterViewlModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCharacterFragment : BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {


    override val viewModel: DetailsCharacterViewModel by viewModels()

    override fun getViewBindind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)

    }


