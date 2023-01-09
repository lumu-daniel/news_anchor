package com.kdl.newsanchor.domain.usecases

import com.kdl.newsanchor.common.Resource
import com.kdl.newsanchor.data.remote.dtos.toNewsItem
import com.kdl.newsanchor.data.repositories.AccessRepository
import com.kdl.newsanchor.domain.models.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchNewsUseCase @Inject constructor(
    private val accessRepository: AccessRepository
){

    companion object{
        var totalPageSize = 0
    }

    operator fun invoke(query:String,page:Int): Flow<Resource<List<NewsItem>>> = flow{
        try {
            emit(Resource.Loading())
            accessRepository.getNewsItems(query,page)?.let {res->
                totalPageSize = res.totalResults
                emit(Resource.Success(res.articles.map { it.toNewsItem() }))
            }

        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage?:"An Error Occurred"))
        }
    }
}