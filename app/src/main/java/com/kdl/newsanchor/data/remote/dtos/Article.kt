package com.kdl.newsanchor.data.remote.dtos

import com.kdl.newsanchor.domain.models.NewsItem

data class Article(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)

fun Article.toNewsItem(): NewsItem {
    return NewsItem(
        author = author,
        title = title,
        url = url,
        source = source,
        urlToImage = urlToImage,
        content = content
    )
}