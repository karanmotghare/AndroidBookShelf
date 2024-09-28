package com.example.myapplication.booklistpage.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModel
import com.example.myapplication.booklistpage.presentation.viewmodels.YourBooksViewModelFactory

class YourBookFragment : Fragment() {
    private lateinit var yourBooksViewModel: YourBooksViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val factory = YourBooksViewModelFactory()
        yourBooksViewModel = ViewModelProvider(this, factory).get(YourBooksViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_your_book, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn: Button = view.findViewById(R.id.add_btn)
        val del: Button = view.findViewById(R.id.del_btn)

        btn.setOnClickListener{
            yourBooksViewModel.addBook(BooklistDataModel("1","first",34.0,21421,"first book",21435))
        }
        del.setOnClickListener{
            yourBooksViewModel.delete("1")
        }


    }
}