package com.rashwan.accunews.utils

import android.text.format.DateUtils
import android.widget.RadioButton
import android.widget.RadioGroup
import java.text.SimpleDateFormat
import java.util.*


object AppUtils {

    fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()
    fun getRelativeTimeStr(date: Date): CharSequence {
        val currentTime = System.currentTimeMillis()
        return DateUtils.getRelativeTimeSpanString(
            date.time,
            currentTime,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_RELATIVE
        )
    }


    fun strToDate(dateString: String): Date {
        val format = "yyyy-MM-dd'T'HH:mm:ss'Z'"
        val formatter = SimpleDateFormat(format)
        return formatter.parse(dateString)!!

    }

    fun countryTwoLetters(country: String): String {
        return when (country) {
            "USA" -> "us"
            "Italy" -> "it"
            "France" -> "fr"
            "India" -> "in"
            "Egypt" -> "eg"
            "Kuwait" -> "ar"
            else -> {
                "us"

            }
        }

    }

    fun setRadioButtonCheckedByText(radioGroup: RadioGroup, text: String) {
        val radioButtonCount = radioGroup.childCount
        for (i in 0 until radioButtonCount) {
            val radioButton = radioGroup.getChildAt(i)
            if (radioButton is RadioButton && radioButton.text == text) {
                radioButton.isChecked = true
                return // Once the RadioButton is found and checked, exit the loop
            }
        }
    }
}
