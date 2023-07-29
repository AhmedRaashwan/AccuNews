package com.rashwan.accunews.ui.base

import android.view.View

interface BaseListener {
    fun startLoading(viewLoader: View)
    fun stopLoading(viewLoader: View)
    fun showShortToast(msg: String)
    fun showLongToast(msg: String)
    fun showAlertDialog(msg: String, callBack: (() -> Unit)?)
}