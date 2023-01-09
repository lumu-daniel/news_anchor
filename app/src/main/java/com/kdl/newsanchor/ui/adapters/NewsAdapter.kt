package com.kdl.newsanchor.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import com.kdl.newsanchor.R
import com.kdl.newsanchor.domain.models.NewsItem
import kotlinx.android.synthetic.main.news_item.view.*
import javax.inject.Inject

class NewsAdapter @Inject constructor(
    val glide: RequestManager
):RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder>() {

    inner class NewsItemViewHolder(itemView: View): ViewHolder(itemView)

    private val callBack = object :DiffUtil.ItemCallback<NewsItem>(){
        override fun areItemsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: NewsItem, newItem: NewsItem): Boolean {
            return oldItem==newItem
        }
    }

    private val differ = AsyncListDiffer<NewsItem>(this, callBack)

    var newsList: List<NewsItem>?
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return newsList!!.size
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val item = newsList!![position]
        holder.itemView.apply {
            ivNewsImage?.apply {
                shapeAppearanceModel = ivNewsImage.let {
                    shapeAppearanceModel
                        .toBuilder()
                        .setAllCornerSizes(20f)
                        .build()
                }
            }
            glide.load(item.urlToImage).into(ivNewsImage)
            tvSourceName.text = item.source?.name ?: ""
            tvTitle.text = item.title
            tvAuthor.text = item.author
            setOnClickListener {
                onItemClickListener?.let {
                    it(item)
                }
            }
        }
    }

    private var onItemClickListener: ((NewsItem)->Unit)? = null

    fun setItemClickListener(listener: (NewsItem)->Unit){
        onItemClickListener = listener
    }
}