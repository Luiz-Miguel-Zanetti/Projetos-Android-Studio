package com.example.testelistadetarefas.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import androidx.viewpager2.widget.ViewPager2
import com.example.testelistadetarefas.IntroductionPagerAdapter
import com.example.testelistadetarefas.databinding.ActivityIntroductionBinding
import com.example.testelistadetarefas.databinding.IntroSlide1Binding
import com.example.testelistadetarefas.databinding.IntroSlide2Binding
import com.google.firebase.auth.FirebaseAuth


class IntroductionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIntroductionBinding

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private lateinit var buttonStart : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val viewPager = binding.viewPager
        verifyLoggedUser()
        val layouts = initializeListViewBinding()
        configAdapterViewPager(layouts, viewPager)
    }


    private fun initializeListViewBinding(): List<ViewBinding> {

        val introSlide1Binding = IntroSlide1Binding.inflate(layoutInflater)
        val introSlide2Binding = IntroSlide2Binding.inflate(layoutInflater)

        buttonStart = introSlide2Binding.buttonStart
        buttonStart.setOnClickListener {
            goToLoginActivity()
        }

        return listOf(
            introSlide1Binding,
            introSlide2Binding
        )
    }

    private fun configAdapterViewPager(
        layouts: List<ViewBinding>,
        viewPager: ViewPager2
    ) {
        val adapter = IntroductionPagerAdapter(layouts)
        viewPager.adapter = adapter
    }



    fun goToLoginActivity() {


        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)

    }


    private fun verifyLoggedUser() {

        val currentUser = auth.currentUser

        if (currentUser != null) {

            val intent = Intent(this, TaskListActivity::class.java)
            startActivity(intent)
            finish()

        }

    }


}
