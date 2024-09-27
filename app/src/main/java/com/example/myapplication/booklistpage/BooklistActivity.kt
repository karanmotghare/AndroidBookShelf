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
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.LibraryResultEvent
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.repository.BookListRepo
import com.example.myapplication.booklistpage.domain.usecases.BookListUseCase
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.BookListViewModelFactory
import com.example.myapplication.databinding.ActivityBooklistBinding
import com.example.myapplication.loginpage.viewmodels.AuthState
import com.example.myapplication.loginpage.viewmodels.AuthViewModel

class BooklistActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var binding: ActivityBooklistBinding

    private val viewModelFactory by lazy {
        BookListViewModelFactory(
            BookListUseCase(
                BookListRepo()
            )
        )
    }

    private val viewModel: BookListViewModel by lazy {
        ViewModelProvider(viewModelStore, viewModelFactory).get(BookListViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_booklist)
        setSupportActionBar(binding.toolbar)
        loadData()
        observer()
    }

    private fun loadData() {
        viewModel.getBookListData()
    }

    private fun observer() {
        viewModel.bookListData.observe(this){result ->
           when(result){
               is LibraryResultEvent.OnSuccess ->{
                   val bookList = result.data
               }
               is LibraryResultEvent.OnFailure ->{

               }
               is LibraryResultEvent.OnLoading -> {

               }
           }
        }
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