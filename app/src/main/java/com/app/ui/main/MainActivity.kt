package com.app.ui.main

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.galleryimage.R
import com.app.model.main.PhotoListModel
import com.app.util.NetworkResource
import com.app.viewmodels.ViewModelProviderFactory
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity()
{
    @Inject lateinit var providerFactory: ViewModelProviderFactory
    private lateinit var mainViewModel : MainViewModel
    private val mainAdapter = MainAdapter()

    private val TAG : String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProviders.of(this,providerFactory).get(MainViewModel::class.java)

        observeLiveData()
        initializeList()

    }

    private fun observeLiveData() {
        //observe live data emitted by view model
        mainViewModel.getPosts().observe(this, Observer {
            Log.e(TAG, "loadImageData Success: "+it.toString())
            mainAdapter.submitList(it)
        })
    }

    private fun initializeList() {
        image_recylv.layoutManager = LinearLayoutManager(this)
        image_recylv.adapter = mainAdapter
    }
}
