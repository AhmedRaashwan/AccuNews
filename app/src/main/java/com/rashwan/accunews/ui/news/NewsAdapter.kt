package com.rashwan.accunews.ui.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rashwan.accunews.R
import com.rashwan.accunews.entities.Article
import com.rashwan.accunews.utils.AppUtils

class NewsAdapter() :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    val onNewsItemClickedLiveData = MutableLiveData<Article>()
    private var newsList: List<Article> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_news_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    fun setItems(newsList: List<Article>) {
        this.newsList = newsList
        notifyDataSetChanged()
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.bindView(currentItem)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val myItemView: CardView = view.findViewById(R.id.itemView)
        private val ivPosterImage: ImageView = view.findViewById(R.id.ivImage)
        private val tvNewsTitle: TextView = view.findViewById(R.id.tvNewsTitle)
        private val tvNewsDate: TextView = view.findViewById(R.id.tvNewsDate)
        private val tvNewsSource: TextView = view.findViewById(R.id.tvNewsSource)
        private val tvAuthor: TextView = view.findViewById(R.id.tvAuthor)

        fun bindView(newsEntity: Article) {
            with(newsEntity) {
                val context = itemView.context
                tvNewsTitle.text = title
                tvNewsDate.text = AppUtils.getRelativeTimeStr(AppUtils.strToDate(publishedAt))
                tvNewsSource.text = source?.name
                tvAuthor.text = author
                Glide.with(context)
                    .load(urlToImage)
                    .placeholder(R.drawable.image_details_placeholder)
                    .error(R.drawable.image_details_placeholder)
                    .into(ivPosterImage)
                myItemView.setOnClickListener {
                    onNewsItemClickedLiveData.postValue(this)
                }
            }
        }
    }
}