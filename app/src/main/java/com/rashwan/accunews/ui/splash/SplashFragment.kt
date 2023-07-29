package com.rashwan.accunews.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.rashwan.accunews.MainActivity
import com.rashwan.accunews.R
import com.rashwan.accunews.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as MainActivity).hideActionBar()

        initSplashLiveData()
    }

    private fun initSplashLiveData() {
        viewModel.splashLiveData.observe(viewLifecycleOwner) {
            navigateToNewsFragment()
        }
    }

    private fun navigateToNewsFragment() =
        findNavController().navigate(R.id.action_splashFragment_to_newsFragment)
}