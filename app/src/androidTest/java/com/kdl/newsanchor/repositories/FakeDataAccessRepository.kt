package com.kdl.newsanchor.repositories

import com.kdl.newsanchor.data.remote.dtos.NewsItemDTO
import com.kdl.newsanchor.data.repositories.AccessRepository

class FakeDataAccessRepository:AccessRepository {

    val testData = NewsItemDTO(
        listOf(),
        "",
        1
    )


    override suspend fun getNewsItems(query: String, page: Int): NewsItemDTO {
        TODO("Not yet implemented")
    }
}