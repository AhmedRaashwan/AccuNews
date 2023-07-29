package com.rashwan.accunews.ui.news_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.rashwan.accunews.AppConst
import com.rashwan.accunews.MainActivity
import com.rashwan.accunews.R
import com.rashwan.accunews.entities.Article
import com.rashwan.accunews.ui.base.BaseFragment
import com.rashwan.accunews.utils.AppUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_details.*

@AndroidEntryPoint
class NewsDetailsFragment : BaseFragment() {

    private val viewModel: NewsDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_details, container, false)
    }


    override fun onStart() {
        super.onStart()
        loadNewsDetails()

    }

    private fun setUpActionBar(title: String?) {
        if (activity is MainActivity) {
            val act = (activity as MainActivity)
            act.hideActionBar()

            toolbar.title = title
            act.setSupportActionBar(toolbar)

        }
    }

    private fun loadNewsDetails() {
        arguments?.let {
            val newsEntity =
            Gson().fromJson(it.getString(AppConst.INTENT_NEWS_ENTITY), Article::class.java)
            setUpActionBar(newsEntity.title)
            loadwebview(newsEntity)
            renderData(newsEntity)

        }
    }

    private fun loadwebview(newsEntity: Article) {
        wvNewsDetails.webViewClient = WebViewClient()
        wvNewsDetails.loadUrl(newsEntity.url)
        wvNewsDetails.settings.javaScriptEnabled = true
        wvNewsDetails.settings.setSupportZoom(false)
//        wvNewsDetails.settings.

    }

    inner class WebViewClient : android.webkit.WebViewClient() {

        // Load the URL
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return false
        }

        // ProgressBar will disappear once page is loaded
        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            try {
                pbLoadingNd.visibility = View.GONE
                wvNewsDetails.visibility = View.VISIBLE
            } catch (e: java.lang.Exception) {
            }

        }
    }

    private fun renderData(newsEntity: Article) {
        tvSource.text=newsEntity.author
        with(newsEntity) {
            Glide.with(this@NewsDetailsFragment)
                .load(urlToImage)
                .placeholder(R.drawable.image_banner_placeholder)
                .error(R.drawable.image_banner_placeholder)
                .into(ivBackImage)
            tvDescription.text = newsEntity.title
        }
    }


}