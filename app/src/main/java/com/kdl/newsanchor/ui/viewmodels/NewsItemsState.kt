package com.kdl.newsanchor.ui.viewmodels

import com.kdl.newsanchor.domain.models.NewsItem

data class NewsItemsState(
    val isLoading: Boolean = false,
    val newsItems: List<NewsItem>? = emptyList(),
    val error: String = ""
)