package com.kdl.newsanchor.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kdl.newsanchor.common.Resource
import com.kdl.newsanchor.domain.usecases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: SearchNewsUseCase
): ViewModel() {

    private val _newItemsState = MutableLiveData<NewsItemsState>()
    val newItemsState: LiveData<NewsItemsState> = _newItemsState
    var pageNumber = 1

    fun searchForNews(query:String){
        useCase(query,pageNumber).onEach {result->
            when(result){
                is Resource.Success ->{
                    pageNumber++
                    _newItemsState.value = NewsItemsState(newsItems = result.data?: emptyList())
                }
                is Resource.Error ->{
                    pageNumber++
                    _newItemsState.value = NewsItemsState(
                        error = result.message?:"An Unknown Error Occurred"
                    )
                }
                is Resource.Loading ->{
                    _newItemsState.value = NewsItemsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}