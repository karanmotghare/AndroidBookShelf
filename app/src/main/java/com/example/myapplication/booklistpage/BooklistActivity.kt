package com.example.myapplication.booklistpage

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityBooklistBinding
import com.example.myapplication.loginpage.viewmodels.AuthState
import com.example.myapplication.loginpage.viewmodels.AuthViewModel

class BooklistActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityBooklistBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booklist)
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_booklist,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                signOut()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun signOut(){
        authViewModel.signOut()
        when (authViewModel.authState.value) {
            is AuthState.Unauthenticated -> logout()
            is AuthState.Error -> Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    private fun logout(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}