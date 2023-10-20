package com.example.testelistadetarefas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.testelistadetarefas.databinding.ActivityLoginBinding
import com.example.testelistadetarefas.databinding.IntroSlide1Binding
import com.example.testelistadetarefas.databinding.IntroSlide2Binding

class IntroductionPagerAdapter(private val layouts: List<ViewBinding>) :
    RecyclerView.Adapter<IntroductionPagerAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = layouts[viewType]
        val view = binding.root

        // Configurar os parâmetros de layout para MATCH_PARENT
        view.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Configure suas visualizações com base na posição
        when (position) {
            0 -> {
                if (holder.binding is IntroSlide1Binding) {
                    val introSlide1Binding = holder.binding
                    // Aqui você pode acessar as visualizações do layout IntroSlide1Binding e configurá-las
                }
            }
            1 -> {
                if (holder.binding is IntroSlide2Binding) {
                    val introSlide2Binding = holder.binding
                    // Aqui você pode acessar as visualizações do layout IntroSlide2Binding e configurá-las
                }
            }

            }

    }

    override fun getItemCount(): Int {
        return layouts.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
