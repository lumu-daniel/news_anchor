package com.kdl.newsanchor.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.kdl.newsanchor.R
import com.kdl.newsanchor.domain.models.NewsItem
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_article.*
import javax.inject.Inject

@AndroidEntryPoint
class FragmentArticle: Fragment(R.layout.fragment_article) {

    lateinit var newsItem: NewsItem

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            newsItem = (it.getSerializable("new_item") as NewsItem)
        }
        ivFullNewsImage?.apply {
            shapeAppearanceModel = ivFullNewsImage.let {
                shapeAppearanceModel
                    .toBuilder()
                    .setAllCornerSizes(20f)
                    .build()
            }
        }
        glide.load(newsItem.urlToImage).into(ivFullNewsImage)
        tvFullTitle.text = newsItem.title?:""
        tvFullAuthor.text = newsItem.author?:""
        tvFullSourceName.text = newsItem.source?.name?:""
        tvContent.text = newsItem.content?:""
        btnSeeMore.setOnClickListener {
            findNavController().navigate(R.id.action_fragmentArticle_to_fragmentNewsDetails, Bundle().apply {
                putString("article_url",newsItem.url)
            })
        }
    }


}