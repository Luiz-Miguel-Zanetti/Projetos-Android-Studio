package com.example.marvelappstarter.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.marvelappstarter.databinding.FragmentListCharacterBinding
import com.example.marvelappstarter.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListCharacterFragment: BaseFragment<FragmentListCharacterBinding, ListCharacterViewModel>() {



    override val viewModel: ListCharacterViewModel by viewModels()

    override fun getViewBindind(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentListCharacterBinding = FragmentListCharacterBinding.inflate(inflater, container, false)
}