package com.kdl.newsanchor.domain.repositories

import com.kdl.newsanchor.data.remote.NewsAnchorApi
import com.kdl.newsanchor.data.remote.dtos.NewsItemDTO
import com.kdl.newsanchor.data.repositories.AccessRepository
import javax.inject.Inject

class AccessRepositoryImpl @Inject constructor(
   private val newsAnchorApi: NewsAnchorApi
): AccessRepository {

    override suspend fun getNewsItems(query:String,page:Int): NewsItemDTO {
        return newsAnchorApi.getNewsItems(query,page)
    }
}