package com.app.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.app.galleryimage.R
import com.app.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import androidx.recyclerview.widget.GridLayoutManager

class MainActivity : DaggerAppCompatActivity()
{
    @Inject lateinit var providerFactory: ViewModelProviderFactory
    // FOR DATA ---
    private lateinit var mainViewModel : MainViewModel
    private val mainAdapter = MainAdapter()

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
         * Initialize the ViewModel
         * */

        mainViewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel::class.java)

        observeLiveData()
        initializeList()

    }

    private fun observeLiveData()
    {
        /*
         * When a new page is available, we call submitList() method
         * of the PagedListAdapter class
         * */

        mainViewModel.getPosts().observe(this, Observer {
            Log.e(TAG, "loadImageData Success: "+it.toString())
            mainAdapter.submitList(it)
        })

        // UPDATE Progress ----
        mainViewModel.networkState!!.observe(this, Observer {

            Log.e(TAG, "networkState: "+it.toString())
        })
    }

    private fun initializeList() {
        /*
         * Setup the adapter class for the RecyclerView
         * */
        image_recylv.layoutManager = GridLayoutManager(this, 2)
        image_recylv.adapter = mainAdapter
    }
}
