package com.app.network

import android.app.Application
import com.app.model.main.MainModel
import com.app.model.main.PhotoListModel
import com.app.model.main.PhotosModel
import com.app.network.main.MainApi
import com.app.ui.main.MainViewModel
import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class MainApiTest
{
    @Mock
    private lateinit var context: Application

    @Mock
    private var mainApi: MainApi? = null

    private lateinit var photoListSuccess: MainModel

    private lateinit var photoListError: MainModel

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        viewModel = MainViewModel(context)

        photoListSuccess = MainModel(
            PhotosModel
                (1,21440,11,
                    mutableListOf(
                        PhotoListModel("48340281827","Birman kitten")
                        ,PhotoListModel("48340233282","20190531_201")
                        ,PhotoListModel("48340252022","Sleepy time")
                        ,PhotoListModel("48339014152","Stray Kitten of Kos, Greece")
                        ,PhotoListModel("48338848576","Stray Cats of Kos, Greece.")
                        ,PhotoListModel("48338729372","Amarillo-1")
                        ,PhotoListModel("48338588251","Amarillo-2")
                        ,PhotoListModel("48338727517","Amarillo-3")
                        ,PhotoListModel("48338586991","Amarillo-4")
                        ,PhotoListModel("48338586461","Amarillo-5"))
            )
        )

        photoListError = MainModel(PhotosModel(0,0,0,mutableListOf()))
    }

    @Test
    fun `search photos by kitten and succeed`() {
        val response = Response.success(photoListSuccess)
        runBlocking {
            `when`(mainApi!!.fetchImageDataAsync(anyString(), anyString(), anyString(),
                anyInt(), anyInt(), anyString(), anyLong())).thenReturn(async { response })
        }

        assertEquals(10, response.body()!!.photos.photo.size)
        assertEquals("Stray Cats of Kos, Greece.", response.body()!!.photos.photo[4].title)
        assertEquals("48338729372",response.body()!!.photos.photo[5].id)
        assertTrue(response.body()!!.photos.page == 1)
        assertTrue(response.body()!!.photos.photo.size == 10)
        assertFalse(response.body()!!.photos.perpage == 10)
        assertFalse(response.body()!!.photos.pages == 21)

        with(viewModel)
        {
            assertTrue(viewModel.getData().value.isNullOrEmpty())
            assertTrue(imgsLiveData.value.isNullOrEmpty())
        }

    }

    @Test
    fun `search photos by by kitten and fail`() {
        val response = Response.success(photoListError)
        runBlocking {
            `when`(mainApi!!.fetchImageDataAsync(anyString(), anyString(), anyString(),
                anyInt(), anyInt(), anyString(), anyLong())).thenReturn(async { response })
        }

        assertEquals(0, response.body()!!.photos.photo.size)
        assertTrue(response.body()!!.photos.page == 0)
        assertTrue(response.body()!!.photos.page == 0)
        assertFalse(response.body()!!.photos.photo.isNotEmpty())
        assertFalse(response.body()!!.photos.pages == 21)
    }
}