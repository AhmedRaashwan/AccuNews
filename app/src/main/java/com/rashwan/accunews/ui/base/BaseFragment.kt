package com.rashwan.accunews.ui.base

import android.app.AlertDialog
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.rashwan.accunews.R

open class BaseFragment : Fragment(), BaseListener {

    override fun startLoading(viewLoader: View) {
        when (viewLoader) {
            is SwipeRefreshLayout -> viewLoader.isRefreshing = true
            is ProgressBar -> viewLoader.visibility = View.VISIBLE
            is TextView -> viewLoader.visibility = View.VISIBLE
        }
    }

    override fun stopLoading(viewLoader: View) {
        when (viewLoader) {
            is SwipeRefreshLayout -> viewLoader.isRefreshing = false
            is ProgressBar -> viewLoader.visibility = View.GONE
            is TextView -> viewLoader.visibility = View.GONE
        }

    }

    override fun showShortToast(msg: String) {
        val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
        toast.show()
    }

    override fun showLongToast(msg: String) {
        val toast = Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG)
        toast.show()
    }

    override fun showAlertDialog(msg: String, callBack: (() -> Unit)?) {
        var alertDialog: AlertDialog? = null

        val builder1 = AlertDialog.Builder(requireContext())
        builder1.setMessage(msg)
        builder1.setCancelable(true)

        builder1.setPositiveButton(getString(R.string.ok)) { _, _ ->
            alertDialog?.dismiss()
            if (callBack != null)
                callBack()
        }

        alertDialog = builder1.create()
        alertDialog.show()
    }
}