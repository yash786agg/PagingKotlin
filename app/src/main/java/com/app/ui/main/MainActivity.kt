package com.app.ui.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.galleryimage.R
import com.app.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager
import com.app.util.Constants.Companion.nullData
import com.app.util.NetworkState
import com.app.util.UiHelper

class MainActivity : DaggerAppCompatActivity()
{
    // FOR DATA ---
    @Inject lateinit var uiHelper: UiHelper
    @Inject lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var mainViewModel : MainViewModel
    private val mainAdapter = MainAdapter()

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Initialize the ViewModel
         * */

        mainViewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel::class.java)

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
