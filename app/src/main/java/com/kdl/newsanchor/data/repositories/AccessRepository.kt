package com.kdl.newsanchor.data.repositories

import com.kdl.newsanchor.data.remote.dtos.NewsItemDTO

interface AccessRepository {

    suspend fun getNewsItems(query:String, page:Int): NewsItemDTO
}