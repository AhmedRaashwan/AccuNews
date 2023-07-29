package com.rashwan.accunews.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.rashwan.accunews.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : BaseViewModel() {

    val splashLiveData = MutableLiveData<Boolean>()

    init {
        delayOnSplash()
    }

    private fun delayOnSplash() {
        viewModelScope.launch {
            delay(2000)
            splashLiveData.postValue(true)
        }
    }
}