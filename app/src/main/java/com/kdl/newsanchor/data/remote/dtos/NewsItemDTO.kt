package com.kdl.newsanchor.data.remote.dtos

data class NewsItemDTO(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)