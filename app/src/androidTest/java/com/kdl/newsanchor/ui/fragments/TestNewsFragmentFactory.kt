package com.kdl.newsanchor.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

class TestNewsFragmentFactory: FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            FragmentNewsList::class.java.name -> FragmentNewsList()
            FragmentArticle::class.java.name -> FragmentArticle()
            FragmentNewsDetails::class.java.name -> FragmentNewsDetails()
            else -> super.instantiate(classLoader, className)
        }
    }
}