package com.app.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.app.galleryimage.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.GridLayoutManager
import com.app.util.UiHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    // FOR DATA ---
    @Inject lateinit var uiHelper: UiHelper
    private val mainViewModel : MainViewModel by viewModels()
    private val mainAdapter = MainAdapter()
    private var coroutineJob: Job? = null

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
         * When a new data is available, we call submitData() method
         * of the PagingDataAdapter class
         * */

        // Make sure we cancel the previous job before creating a new one
        coroutineJob?.cancel()
        coroutineJob = lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            mainViewModel.imgsLiveData.collectLatest {
                it.let {
                    mainAdapter.submitData(it)
                }
            }
        }

        /*
         * Progress Updater
         * */
        mainAdapter.addLoadStateListener { loadState ->

            /*
            * loadState.refresh - represents the load state for loading the PagingData for the first time.
              loadState.prepend - represents the load state for loading data at the start of the list.
              loadState.append - represents the load state for loading data at the end of the list.
            * */

            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
                showProgressBar(true)
            else {
                showProgressBar(false)

                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }
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
