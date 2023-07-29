package com.rashwan.accunews.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import com.rashwan.accunews.R

object ThemeUtils {

    fun setMyTheme(radioButtonid: Int, context: Context, activity: Activity) {
        var sharedPreferences: SharedPreferences? = null
        sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val selectedTheme = when (radioButtonid) {
            R.id.rB_light -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.rB_dark -> AppCompatDelegate.MODE_NIGHT_YES
            R.id.rB_system -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(selectedTheme)
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putInt("selectedTheme", radioButtonid)
        editor.apply()
        ActivityCompat.recreate(activity) // Recreate the activity to apply the new theme
    }

    fun initTheme(radioButtonid: Int) {

//        to be called in Oncreate()
        val selectedTheme = when (radioButtonid) {
            R.id.rB_light -> AppCompatDelegate.MODE_NIGHT_NO
            R.id.rB_dark -> AppCompatDelegate.MODE_NIGHT_YES
            0 -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }
        AppCompatDelegate.setDefaultNightMode(selectedTheme)
    }

}
