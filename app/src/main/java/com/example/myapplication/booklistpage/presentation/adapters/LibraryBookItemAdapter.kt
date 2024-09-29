package com.example.myapplication.booklistpage.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.booklistpage.data.models.BooklistDataModel
import com.example.myapplication.databinding.ItemBookLayoutBinding

class LibraryBookItemAdapter(private val bookList: List<BooklistDataModel>,
        private val onBookClicked : (BooklistDataModel) -> Unit
    ) : RecyclerView.Adapter<LibraryBookItemAdapter.LibraryBookItemViewHolder>() {
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
            val heartIcon = if(booklistDataModel.isWishListed){
                R.drawable.selected_heart
            }else{
                R.drawable.unselected_heart
            }

            binding.bookTitle.text = booklistDataModel.title?:"No Title"
            binding.publishedDate.text = "Published on "+getYear(booklistDataModel.publishedChapterDate).toString()?:"date not available"
            Glide.with(itemView.context)
                .load(booklistDataModel.img)
                .placeholder(R.drawable.ic_book_icon)
                .error(R.drawable.ic_book_icon)
                .into(binding.bookIcon)
            binding.wishlistBook.setImageResource(heartIcon)
            binding.wishlistBook.setOnClickListener {
                onBookClicked(booklistDataModel)
            }
            binding.bookRating.text = getRating(booklistDataModel.score)
        }

        private fun getRating(score: Double): CharSequence {
            val rating = ((score/ 100)*5)
            return String.format("%.2f", rating)
        }

    }

    private fun getYear(date: Long): Int {
        val calendar = java.util.Calendar.getInstance()
        calendar.timeInMillis = date * 1000 // Convert to milliseconds
        return calendar.get(java.util.Calendar.YEAR)
    }
}