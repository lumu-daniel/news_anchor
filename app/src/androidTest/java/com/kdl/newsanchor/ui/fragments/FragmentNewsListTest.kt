package com.kdl.newsanchor.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.kdl.newsanchor.R
import com.kdl.newsanchor.common.Constants.DEFAULT_SEARCH_TEXT
import com.kdl.newsanchor.domain.usecases.SearchNewsUseCase
import com.kdl.newsanchor.launchFragmentInHiltContainer
import com.kdl.newsanchor.repositories.FakeDataAccessRepository
import com.kdl.newsanchor.ui.viewmodels.MainViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_news_list.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FragmentNewsListTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: TestNewsFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun testDefaultSearchText() {

        val testViewModel = MainViewModel(SearchNewsUseCase(FakeDataAccessRepository()))
        launchFragmentInHiltContainer<FragmentNewsList>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }
        Espresso.onView(ViewMatchers.withId(R.id.etSearchBar)).perform(replaceText(DEFAULT_SEARCH_TEXT))

        assertThat(ViewMatchers.withId(R.id.etSearchBar)).isEqualTo(DEFAULT_SEARCH_TEXT)
    }

    @Test
    fun testSearchBarListener() {
        val testViewModel = MainViewModel(SearchNewsUseCase(FakeDataAccessRepository()))
        launchFragmentInHiltContainer<FragmentNewsList>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.etSearchBar)).perform(replaceText("test"))

        assertThat(ViewMatchers.withId(R.id.etSearchBar)).isEqualTo("test")
    }

    @Test
    fun testOnScrollListener() {
        val testViewModel = MainViewModel(SearchNewsUseCase(FakeDataAccessRepository()))
        var fragment: FragmentNewsList? = null
        launchFragmentInHiltContainer<FragmentNewsList>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
            fragment = this
        }

        // Simulate scrolling to the bottom of the recycler view
        fragment?.checkLoading = false
        fragment?.checkLastPage = false
        fragment?.checkScrolling = true
        fragment?.onScrollListener?.onScrolled(fragment!!.news_list, 0, 0)
        assertTrue(fragment?.checkLoading?:false)

        // Test that the function is not called when the checkScrolling flag is false
        fragment?.checkLoading = false
        fragment?.checkScrolling = false
        fragment?.onScrollListener?.onScrolled(fragment!!.news_list, 0, 0)
        assertFalse(fragment?.checkLoading?:false)

    }
}