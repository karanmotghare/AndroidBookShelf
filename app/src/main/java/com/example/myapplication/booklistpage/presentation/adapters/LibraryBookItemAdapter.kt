package com.example.myapplication.booklistpage.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.databinding.ItemBookLayoutBinding

class LibraryBookItemAdapter(private val bookList: List<BooklistDataModel>) : RecyclerView.Adapter<LibraryBookItemAdapter.LibraryBookItemViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryBookItemViewHolder {
        val binding = ItemBookLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LibraryBookItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibraryBookItemViewHolder, position: Int) {
        holder.bind(bookList[position])
    }


    override fun getItemCount(): Int {
       return bookList.size
    }

    inner class LibraryBookItemViewHolder(
        val binding: ItemBookLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(booklistDataModel: BooklistDataModel){
            binding.bookTitle.text = booklistDataModel.title
            binding.publishedDate.text = "Published on "+getYear(booklistDataModel.publishedChapterDate).toString()
        }

    }

    private fun getYear(date: Long): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = date * 1000 // Convert to milliseconds
        return calendar.get(java.util.Calendar.YEAR)
    }
}