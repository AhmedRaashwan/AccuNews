package com.rashwan.accunews

import android.content.Context
import android.os.Bundle
import com.rashwan.accunews.ui.base.BaseActivity
import com.rashwan.accunews.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUserTheme()
        setContentView(R.layout.activity_main)
        setUpActionBar()
    }

    private fun initUserTheme() {
        val sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val spSelectedTheme: Int = sharedPreferences?.getInt("selectedTheme", 0)!!
        ThemeUtils.initTheme(spSelectedTheme)
    }

    fun setUpActionBar() {
        setSupportActionBar(toolbar)
    }

    private fun showActionBar() {
        supportActionBar?.show()
    }

    fun hideActionBar() {
        supportActionBar?.hide()
    }

    fun setActionBarTitle(title: String?, isDisplayHome: Boolean) {
        showActionBar()
        supportActionBar?.setDisplayHomeAsUpEnabled(isDisplayHome)
        supportActionBar?.title = title
        if (isDisplayHome)
            toolbar.setNavigationOnClickListener { onBackPressed() }
    }
}