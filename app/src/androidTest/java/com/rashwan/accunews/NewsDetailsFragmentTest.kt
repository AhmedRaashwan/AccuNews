package com.rashwan.accunews

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson
import com.rashwan.accunews.entities.Article
import com.rashwan.accunews.ui.news_details.NewsDetailsFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NewsDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testNavigateToNewsDetailsFragment_DisplayedOnUI() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        val bundle = Bundle()
        val articles = Article(
            author = "String",
            title = "String",
            description = "String",
            url = "String",
            urlToImage = "String",
            publishedAt = "String",
            content = "String"
        )

        bundle.putString(AppConst.INTENT_NEWS_ENTITY, Gson().toJson(articles))
        launchFragmentInHiltContainer<NewsDetailsFragment>(bundle, R.style.AppTheme) {
            navController.setGraph(R.navigation.nav_graph_main)
            navController.setCurrentDestination(R.id.newsDetailsFragment)
            Navigation.setViewNavController(requireView(), navController)
        }

        Thread.sleep(1000)
        onView(withId(R.id.tvDescription)).check(matches(withText(articles.description)))
    }


}