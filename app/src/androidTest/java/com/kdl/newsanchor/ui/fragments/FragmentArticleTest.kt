package com.kdl.newsanchor.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.kdl.newsanchor.R
import com.kdl.newsanchor.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class FragmentArticleTest {

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
    fun clickSeeMore_GoesToFragmentNewsDetails() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<FragmentArticle>(
            fragmentFactory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(withId(R.id.btnSeeMore)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(R.id.action_fragmentArticle_to_fragmentNewsDetails)
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<FragmentArticle>(
            fragmentFactory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()
    }

}