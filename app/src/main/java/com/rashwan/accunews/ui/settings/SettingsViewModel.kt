package com.rashwan.accunews.ui.settings

import androidx.lifecycle.MutableLiveData
import com.rashwan.accunews.entities.SettingsEntity
import com.rashwan.accunews.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor() : BaseViewModel() {
    val settingsLiveData: MutableLiveData<SettingsEntity> =
        MutableLiveData<SettingsEntity>(
            SettingsEntity()
        )
}