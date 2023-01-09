package com.kdl.newsanchor.domain.models

import com.kdl.newsanchor.data.remote.dtos.Source
import java.io.Serializable

data class NewsItem (
    val author: String?,
    val title: String?,
    val url: String?,
    val source: Source?,
    val urlToImage: String?,
    val content: String?
):Serializable