package com.app.ui.main

import androidx.paging.PagingSource.LoadResult.Page
import androidx.paging.PagingSource
import com.app.galleryimage.BuildConfig
import com.app.model.main.PhotoListModel
import com.app.network.main.MainApi
import com.app.util.Constants.Companion.flickrPhotosSearch
import com.app.util.Constants.Companion.format
import com.app.util.Constants.Companion.kittenSearch
import com.app.util.Constants.Companion.noJsonCallback
import com.app.util.Constants.Companion.perPage
import javax.inject.Inject

class MainDataSourceClass @Inject constructor(private val mainApi: MainApi): PagingSource<Int, PhotoListModel>() {

    private val initialPageIndex: Int = 1

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoListModel> {
        val position = params.key ?: initialPageIndex
        return try {
            val response = mainApi.fetchImageDataAsync(flickrPhotosSearch, BuildConfig.API_Key,kittenSearch, position,
                perPage, format, noJsonCallback).await()

            val items = response.body()?.photos
            Page(
                data = items?.photo!!,
                prevKey = if (position == initialPageIndex) null else position - 1,
                nextKey = if (items.photo.isEmpty()) null else position + 1
            )
        } catch (exception : Exception) {
            LoadResult.Error(exception)
        }
    }
}