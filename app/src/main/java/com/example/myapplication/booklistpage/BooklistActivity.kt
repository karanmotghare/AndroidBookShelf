package com.example.myapplication.booklistpage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapplication.R
import com.example.myapplication.booklistpage.presentation.ui.HomeFragment
import com.example.myapplication.databinding.ActivityBooklistBinding

class BooklistActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBooklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booklist)
        openFragment()
    }

    private fun openFragment() {
        val fragment = HomeFragment()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

}