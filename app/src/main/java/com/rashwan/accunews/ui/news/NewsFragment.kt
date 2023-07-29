package com.rashwan.accunews.ui.news

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.rashwan.accunews.AppConst
import com.rashwan.accunews.MainActivity
import com.rashwan.accunews.R
import com.rashwan.accunews.entities.Article
import com.rashwan.accunews.entities.SettingsEntity
import com.rashwan.accunews.ui.base.BaseFragment
import com.rashwan.accunews.ui.settings.SettingsFragment
import com.rashwan.accunews.ui.settings.SettingsViewModel
import com.rashwan.accunews.utils.AppUtils.countryTwoLetters
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsFragment : BaseFragment() {
    private val viewModel: NewsViewModel by activityViewModels()
    private val settingViewModel: SettingsViewModel by activityViewModels()
    private lateinit var adapter: NewsAdapter
    var sharedPreferences: SharedPreferences? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_news, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupAdapter()
        initSwipeToRefresh()
        initNewsLiveData()
        initNewsClickLiveData()
        initSettingsLiveData()
    }

    override fun onStart() {
        super.onStart()
        if (activity is MainActivity)
            setUpActionBar()

        //get the config from SharedPreferences if exists
        sharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val spSelectedCountry: String? = sharedPreferences?.getString("selectedCountry", "us")
        val spSelectedCategory: String? =
            sharedPreferences?.getString("selectedCategory", "general")
        val myConfig = SettingsEntity(countryTwoLetters(spSelectedCountry!!), spSelectedCategory!!)
        settingViewModel.settingsLiveData.value = myConfig
        loadAccuNews()


    }

    private fun setUpActionBar(title: String? = null) {
        val act = activity as MainActivity
        act.setUpActionBar()
        act.setActionBarTitle(title ?: getString(R.string.accu_news), false)
    }

    private fun setupAdapter() {
        adapter = NewsAdapter()
        rvItems.adapter = adapter
    }

    private fun initSwipeToRefresh() {
        swipeToRefresh.setOnRefreshListener {
            viewModel.loadNews(settingViewModel.settingsLiveData.value, context)
        }
    }

    private fun initNewsLiveData() {
        viewModel.newsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                renderData(it.articles)
                stopLoading(swipeToRefresh)
            }
        }
    }

    private fun initNewsClickLiveData() {
        adapter.onNewsItemClickedLiveData.observe(viewLifecycleOwner) {
            val arg = setUpBundle(it)
            findNavController().navigate(
                R.id.action_newsFragment_to_newsDetailsFragment, arg
            )
        }
    }

    private fun initSettingsLiveData() {
        settingViewModel.settingsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.newsLiveData.value = null
                loadAccuNews()
                stopLoadingDueToErr()
            }
        }
    }

    private fun stopLoadingDueToErr() {
        viewModel.newsErrLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                if (viewModel.newsErrLiveDate.value == true) {
                    stopLoading(swipeToRefresh)
                    startLoading(tvErrLoading)
                    Toast.makeText(
                        context, getString(R.string.please_check_apikey_toast),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    stopLoading(tvErrLoading)
                }
            }
        }
        viewModel.newsExpLiveDate.observe(viewLifecycleOwner) {
            it?.let {
                if (viewModel.newsExpLiveDate.value == getString(R.string.http401)) {
                    tvErrLoading!!.text =
                        viewModel.newsExpLiveDate.value + getString(R.string.please_check_the_apikey)
                } else {
                    tvErrLoading!!.text = viewModel.newsExpLiveDate.value
                }
            }
        }
    }

    private fun loadAccuNews() {
        if (viewModel.newsLiveData.value == null) {
            startLoading(swipeToRefresh)
            viewModel.loadNews(
                settingViewModel.settingsLiveData.value,
                context
            )
        } else
            viewModel.newsLiveData.value = viewModel.newsLiveData.value
        settingViewModel.settingsLiveData.observe(viewLifecycleOwner) {
            it?.let {
                if (viewModel.newsErrLiveDate.value == true) stopLoading(swipeToRefresh)
            }
        }
    }

    private fun renderData(data: List<Article>) {
        lifecycleScope.launch {
            adapter.setItems(data)
        }

        lifecycleScope.launch {
            try {
                delay(500)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }


    private fun setUpBundle(article: Article): Bundle {
        val arg = Bundle()
        arg.putString(AppConst.INTENT_NEWS_ENTITY, Gson().toJson(article))
        return arg
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_news, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.menu_item_settings)
            SettingsFragment.showDialogFragment(requireActivity())
        return super.onOptionsItemSelected(item)
    }
}