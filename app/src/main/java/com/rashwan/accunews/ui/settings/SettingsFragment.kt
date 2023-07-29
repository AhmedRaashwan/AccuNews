package com.rashwan.accunews.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rashwan.accunews.MainActivity
import com.rashwan.accunews.R
import com.rashwan.accunews.entities.SettingsEntity
import com.rashwan.accunews.utils.AppUtils
import com.rashwan.accunews.utils.AppUtils.countryTwoLetters
import com.rashwan.accunews.utils.ThemeUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*


@Suppress("UNSAFE_CALL_ON_PARTIALLY_DEFINED_RESOURCE")
@AndroidEntryPoint
class SettingsFragment : BottomSheetDialogFragment() {
    private val viewModel: SettingsViewModel by activityViewModels()
    var sharedPreferences: SharedPreferences? = null
    var activity: MainActivity? = null

    companion object {
        fun showDialogFragment(activity: FragmentActivity?) {
            val bottomSheetDialogFragment = SettingsFragment()
            bottomSheetDialogFragment.show(
                activity?.supportFragmentManager!!,
                bottomSheetDialogFragment.tag
            )
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) activity = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {

        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        initViews()
    }

    private fun initViews() {
        // get data from shared preference
        tvNewsCountry.text = getString(R.string.news_country)
        tvNewsCategory.text = getString(R.string.news_category)
        tvtheme.text = getString(R.string.lbl_appTheme)

        val spSelectedCountry: String? = sharedPreferences?.getString("selectedCountry", "USA")
        val spSelectedCategory: String? =
            sharedPreferences?.getString("selectedCategory", "general")
        val spSelectedTheme: Int? = sharedPreferences?.getInt("selectedTheme", rB_system.id)
        AppUtils.setRadioButtonCheckedByText(country_radioGroup, spSelectedCountry!!)
        AppUtils.setRadioButtonCheckedByText(category_radioGroup, spSelectedCategory!!)
        theme_radioGroup.check(spSelectedTheme!!)

        btApply.setOnClickListener {
            setNewsCountryAndCategory()
            setTheme()
        }
    }

    private fun setNewsCountryAndCategory() {
        if (country_radioGroup.checkedRadioButtonId != -1 && category_radioGroup.checkedRadioButtonId != -1) {
            val countryRadio: RadioButton =
                country_radioGroup.findViewById(country_radioGroup.checkedRadioButtonId)
            val categoryRadio: RadioButton =
                category_radioGroup.findViewById(category_radioGroup.checkedRadioButtonId)
            val selectedCountry = countryTwoLetters(countryRadio.text.toString())
            val selectedCategory = categoryRadio.text.toString()
            viewModel.settingsLiveData.value = SettingsEntity(selectedCountry, selectedCategory)
//            Storing values to Shared preference
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
            editor.putString("selectedCountry", countryRadio.text.toString())
            editor.putString("selectedCategory", selectedCategory)
            editor.apply()
        }
        dismiss()
    }


    private fun setTheme() {
        ThemeUtils.setMyTheme(theme_radioGroup.checkedRadioButtonId, requireContext(), activity!!)
    }

}