package com.example.marvelappstarter.ui.adapter

import android.nfc.tech.NfcA.get
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.viewmodel.savedstate.R
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marvelappstarter.data.model.character.CharacterModel
import com.example.marvelappstarter.data.model.comic.ComicModel
import com.example.marvelappstarter.databinding.FragmentDetailsCharacterBinding
import com.example.marvelappstarter.databinding.ItemCharacterBinding
import com.example.marvelappstarter.databinding.ItemComicBinding
import com.example.marvelappstarter.util.limitDescription

class ComicrAdapter : RecyclerView.Adapter<ComicrAdapter.ComicViewHolder>() {

    inner class ComicViewHolder(val binding: ItemComicBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<ComicModel>() {

        override fun areItemsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: ComicModel, newItem: ComicModel): Boolean {
            return oldItem.id == newItem.id && oldItem.title == newItem.title && oldItem.description == newItem.description &&
                    oldItem.thumbnailModel.path == newItem.thumbnailModel.path && oldItem.thumbnailModel.extesion == newItem.thumbnailModel.extesion
        }
    }

    private val differ = AsyncListDiffer(this, differCallback)

    var comic: List<ComicModel>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        return ComicViewHolder(
            ItemComicBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int = comic.size

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        val comic = comic[position]
        holder.binding.apply {
            tvNameComic.text = comic.title
            tvDescriptionComic.text = comic.description
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(comic)
            }
        }
    }

    private var onItemClickListener: ((ComicModel) -> Unit)? = null

    fun setOnClickListener(listener: (ComicModel) -> Unit) {
        onItemClickListener = listener
    }

    fun getCharacterPosition(position: Int): ComicModel {
        return comic[position]
    }
}

