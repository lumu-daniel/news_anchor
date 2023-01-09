package com.kdl.newsanchor.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kdl.newsanchor.R
import com.kdl.newsanchor.common.Constants.DEFAULT_SEARCH_TEXT
import com.kdl.newsanchor.common.Constants.PAGE_ITEM_SIZE
import com.kdl.newsanchor.common.Constants.SEARCH_NEWS_DELAY
import com.kdl.newsanchor.domain.usecases.SearchNewsUseCase.Companion.totalPageSize
import com.kdl.newsanchor.ui.adapters.NewsAdapter
import com.kdl.newsanchor.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class FragmentNewsList:Fragment(R.layout.fragment_news_list) {

    lateinit var viewModel: MainViewModel

    @Inject
    lateinit var newsAdapter: NewsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        etSearchBar.setText(DEFAULT_SEARCH_TEXT)
        viewModel.searchForNews(etSearchBar.text.toString())
        setUpRecyclerView()
        subscribeToObservers()
        setSearchListner()
        setSwipeToRefresh()
    }

    private fun subscribeToObservers(){
        viewModel.newItemsState.observe(viewLifecycleOwner){it->
            loader.visibility = View.GONE
            tvError.visibility = View.GONE
            it?.let {result->
                if(result.newsItems!!.isNotEmpty()){
                    swipeLayout.isRefreshing = false
                    newsAdapter.newsList = result.newsItems.toList()
                    val totalPages = totalPageSize / PAGE_ITEM_SIZE + 2
                    checkLastPage = viewModel.pageNumber == totalPages
                }
                if(result.isLoading){
                    loader.visibility = View.VISIBLE
                }
                if(result.error.isNotBlank()){
                    tvError.text = result.error
                    tvError.visibility = View.VISIBLE
                }
            }
        }
    }

    var checkLoading = false
    var checkLastPage = false
    var checkScrolling = false

    val onScrollListener = object : RecyclerView.OnScrollListener(){
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                checkScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !checkLoading && !checkLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= PAGE_ITEM_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && checkScrolling
            if(shouldPaginate) {
                viewModel.searchForNews("Android")
                checkScrolling = false
            } else {
                news_list.setPadding(0, 0, 0, 0)
            }
        }
    }

    private fun setUpRecyclerView() = news_list.apply {
        newsAdapter.setItemClickListener {
            val actionBundle = Bundle().apply {
                putSerializable("new_item",it)
            }
            findNavController().navigate(R.id.action_fragmentNewsList_to_fragmentArticle, actionBundle)
        }
        adapter = newsAdapter
        layoutManager = LinearLayoutManager(requireContext())
        addOnScrollListener(this@FragmentNewsList.onScrollListener)

    }

    private fun setSearchListner() {
        var job: Job? = null
        etSearchBar.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEARCH_NEWS_DELAY)
                editable?.let {
                    if(editable.toString().isNotEmpty()) {
                        viewModel.searchForNews(editable.toString())
                    }
                }
            }
        }
    }

    private fun setSwipeToRefresh() {
        swipeLayout.apply {
            setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)

            setOnRefreshListener {
                var searchText = etSearchBar.text.toString()
                if(searchText.isBlank()){
                    searchText = DEFAULT_SEARCH_TEXT
                }
                viewModel.pageNumber = 1
                viewModel.searchForNews(searchText)
            }
        }
    }
}