package com.jonghyun.bookfinder.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jonghyun.bookfinder.R
import com.jonghyun.bookfinder.data.response.BooksApiVolumesResponse
import kotlinx.android.synthetic.main.line_books_list.view.*


class BookListAdapter : RecyclerView.Adapter<BookListAdapter.BookViewHolder>() {
    interface ItemClickListener {
        fun clickItem(id: String)
    }

    var bookListItemClickListener: ItemClickListener? = null
    var bookList: List<BooksApiVolumesResponse.Volume> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        return BookViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line_books_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.generateView(bookList[position], bookListItemClickListener)
    }

    class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun generateView(item: BooksApiVolumesResponse.Volume, itemClickListener: ItemClickListener?) {
            item.volumeInfo.imageLinks?.let {
                Glide.with(itemView).load(Uri.parse(it.smallThumbnail)).into(itemView.iv_line_books_list_thumbnail)
            }
            item.volumeInfo.authors?.let {
                itemView.actv_line_books_list_author.text = it.joinToString()
            }
            itemView.actv_line_books_list_title.text = item.volumeInfo.title
            itemView.actv_line_books_list_published_date.text = item.volumeInfo.publishedDate
            itemView.setOnClickListener { itemClickListener?.clickItem(item.id) }
        }
    }
}