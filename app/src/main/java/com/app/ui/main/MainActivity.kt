package com.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.galleryimage.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import com.app.util.Constants.Companion.nullData
import com.app.util.NetworkState
import com.app.util.UiHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    // FOR DATA ---
    @Inject lateinit var uiHelper: UiHelper
    private val mainViewModel : MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        if(uiHelper.getConnectivityStatus())
            subscribeObservers()
        else
            uiHelper.showSnackBar(mainActivityRootView, resources.getString(R.string.error_network_connection))
    }

    private fun subscribeObservers()
    {
        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        mainViewModel.getData().observe(this, Observer {
            if(it != null)
                mainAdapter.submitList(it)
        })

        /*
         * Progress Updater
         * */
        mainViewModel.networkState!!.observe(this, Observer {

            if(it != null)
            {
                when(it)
                {
                    is NetworkState.Loading -> showProgressBar(true)
                    is NetworkState.Success -> showProgressBar(false)
                    is NetworkState.Error ->
                    {
                        showProgressBar(false)
                        if(it.message == nullData)
                            uiHelper.showSnackBar(mainActivityRootView, resources.getString(R.string.something_went_wrong))
                        else
                            uiHelper.showSnackBar(mainActivityRootView, resources.getString(R.string.error_network_connection))
                    }
                }
            }
        })
    }

    private fun initRecyclerView() {

        /*
         * Setup the adapter class for the RecyclerView
         * */
        image_recylv.layoutManager = GridLayoutManager(this, 2)
        image_recylv.adapter = mainAdapter
    }

    // UPDATE UI ----
    private fun showProgressBar(display : Boolean)
    {
        if(!display)
            progress_bar.visibility = View.GONE
        else
            progress_bar.visibility = View.VISIBLE
    }
}
