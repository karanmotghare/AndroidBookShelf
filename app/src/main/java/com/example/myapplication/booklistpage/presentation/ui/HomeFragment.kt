package com.example.myapplication.booklistpage.presentation.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModelFactory
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.loginpage.viewmodels.AuthState
import com.example.myapplication.loginpage.viewmodels.AuthViewModel
import com.google.android.material.appbar.MaterialToolbar


class HomeFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private lateinit var binding: FragmentHomeBinding
    private lateinit var yourBooksViewModel: YourBooksViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = YourBooksViewModelFactory()
        yourBooksViewModel = ViewModelProvider(this, factory).get(YourBooksViewModel::class.java)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolbar: MaterialToolbar = binding.toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        ui()
    }

    private fun ui() {
        binding.libraryLayout.setOnClickListener {
            val libraryFragment = LibraryFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, libraryFragment)
                .addToBackStack(null)
                .commit()
        }
        binding.yourBookLayout.setOnClickListener{
            val yourBookFragment = YourBookFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, yourBookFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_booklist, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
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
            is AuthState.Error -> Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    private fun logout(){
        val intent = Intent(requireActivity(), MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        yourBooksViewModel.deleteAll()
        startActivity(intent)
        activity?.finish()
    }

}