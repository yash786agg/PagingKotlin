# PagingKotlin

#### Pagination

The Paging Library helps you in the loading of heavy-data with endless scrolling or infinite scrolling. You can load and display small chunks of data at a time which can eventually reduce the usage of network bandwidth and system resources.

# Demo
![PagingKotlin](screenshots/GalleryImage.gif)

Paging library Installation 

#### Android Gradle
```groovy
// Add Paging dependency
dependencies {
      implementation 'android.arch.paging:runtime:1.0.1'
}
```

#### PagedList

The Paging Library's key component is the PagedList object which loads chunks of your app's data or pages. If any loaded data changes, a new instance of PagedList is emitted to the observable data holder from a LiveData

**setEnablePlaceholders(boolean enablePlaceholders)** — Enabling placeholders mean there is a placeholder that is visible to the user till the data is fully loaded. So for instance, if we have 20 items that are needed to be loaded and each item contains an image when we scroll through the screen, we can see placeholders instead of the image since it is not fully loaded. 

**setPageSize(int pageSize)** — The number of items to load in the PagedList.

```groovy
 init
 {
   val config = PagedList.Config.Builder().setPageSize(20).setEnablePlaceholders(true).build()
   imgsLiveData = initializedPagedListBuilder(config).build()
 }
```

#### DataSource.Factory

You can load the data into PagedList objects by creating a concrete subclass of DataSource.Factory. The following code snippet shows how to generate new instances of the custom data source defined in the preceding code snippet

```groovy
private fun initializedPagedListBuilder(config: PagedList.Config): LivePagedListBuilder<Int, DataModel> {

        val dataSourceFactory = object : DataSource.Factory<Int, DataModel>() {
            override fun create(): DataSourceClass {
                val source = DataSourceClass(api)
                data.postValue(source)
                return source
            }
        }
        return LivePagedListBuilder(dataSourceFactory, config)
    }
```

#### DataSource
This is the base class for data loading, used in list paging.DataSource can be implemented in 3 ways
* PageKeyedDataSource
* ItemKeyedDataSource
* PositionalDataSource

In this scenario, we would be using a PageKeyedDataSource. The following code shows how we can create PageKeyedDataSource for our DataSource class.As we are using  **Int** to load data based on the number of pages in the DataSource

```groovy

override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, DataModel>) {}

override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataModel>) {}

override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataModel>) {}

```

# Prerequisites
* __Android Studio 3.0__
* __Android Device with USB Debugging Enabled__

# Built With

* __[Android Studio](https://developer.android.com/studio/index.html)__ - The Official IDE for Android
* __[Android JetPack Paging library](https://developer.android.com/topic/libraries/architecture/paging)__ - Android JetPack Paging library
* __[Paging Video for Reference](https://www.youtube.com/watch?v=BE5bsyGGLf4)__ Paging Video for Reference
* __[Gradle](https://gradle.org)__ - Build tool for Android Studio
