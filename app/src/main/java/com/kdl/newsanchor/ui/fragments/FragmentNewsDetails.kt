package com.kdl.newsanchor.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.kdl.newsanchor.R
import kotlinx.android.synthetic.main.fragment_new_details.*

class FragmentNewsDetails: Fragment(R.layout.fragment_new_details) {

    private lateinit var mainView: View
    private lateinit var redirect: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainView = view
        progressBar.visibility = View.VISIBLE
        arguments?.let { redirect = it.getString("article_url")?:"" }
        wVDetails.apply {
            settings.apply{
                domStorageEnabled = true
                loadWithOverviewMode = true
                useWideViewPort = true
            }

            webViewClient = WebClient()
            wVDetails.loadUrl(redirect)
        }
    }

    inner class WebClient: WebViewClient(){

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressBar.visibility = View.GONE
        }
    }
}