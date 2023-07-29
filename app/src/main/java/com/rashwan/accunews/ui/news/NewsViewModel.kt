package com.rashwan.accunews.ui.news

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rashwan.accunews.R
import com.rashwan.accunews.data.NewsAPIs
import com.rashwan.accunews.entities.NewsEntity
import com.rashwan.accunews.entities.SettingsEntity
import com.rashwan.accunews.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsAPIs: NewsAPIs) : BaseViewModel() {

    val newsLiveData = MutableLiveData<NewsEntity>()
    val newsErrLiveDate = MutableLiveData<Boolean>()
    val newsExpLiveDate = MutableLiveData<String>()
    fun loadNews(value: SettingsEntity?, context: Context?) = viewModelScope.launch {

//Because using two APi keys,
// I used the below try to check the two keys I have.
        newsErrLiveDate.value = false
        try {
            newsLiveData.value = newsAPIs.loadNews(
                context!!.getString(R.string.apikey),
                value?.newsCountry,
                value?.newsCategory
            )
        } catch (e: Exception) {
            try {
                newsLiveData.value = newsAPIs.loadNews(
                    context!!.getString(R.string.alternative_apikey),
                    value?.newsCountry,
                    value?.newsCategory
                )
            } catch (e: Exception) {
                newsErrLiveDate.value = true
                newsExpLiveDate.value = e.message.toString()
            }

        }
    }
}

