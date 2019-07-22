package com.app.network

import com.app.model.main.LargeSizePhoto
import com.app.model.main.PhotoSizes
import com.app.model.main.Sizes
import com.app.network.main.ImageBindingApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.*
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class ImageBindingApiTest
{
    @Mock
    private var imageBindingApi : ImageBindingApi? = null

    private lateinit var photoSizesSuccess: PhotoSizes

    private lateinit var photoSizesError: PhotoSizes


    @Before
    fun setUp()
    {
        MockitoAnnotations.initMocks(this)

        photoSizesSuccess = PhotoSizes(
            Sizes(mutableListOf(
                LargeSizePhoto("Square","https://live.staticflickr.com/65535/48340281827_5e2d715fc7_s.jpg")
                ,LargeSizePhoto("Large Square","https://live.staticflickr.com/65535/48340281827_5e2d715fc7_q.jpg")
                ,LargeSizePhoto("Thumbnail","https://live.staticflickr.com/65535/48340281827_5e2d715fc7_t.jpg")
                ,LargeSizePhoto("Small","https://live.staticflickr.com/65535/48340281827_5e2d715fc7_m.jpg")
            ))
        )

        photoSizesError = PhotoSizes(Sizes(mutableListOf()))
    }

    @Test
    fun `get Photo Url by id and succeed`()
    {
        val response = Response.success(photoSizesSuccess)
        runBlocking {
            `when`(imageBindingApi!!.fetchSingleImageAsync(anyString(), anyString(),anyString(), anyString(), anyLong()))
                .thenReturn(async { response })
        }

        assertEquals(4, response.body()!!.sizes.size.size)
        assertEquals("Large Square", response.body()!!.sizes.size[1].label)
        assertFalse(response.body()!!.sizes.size[0].source == "")
        assertFalse(response.body()!!.sizes.size.size == 3)
        assertTrue(response.body()!!.sizes.size[1].source == "https://live.staticflickr.com/65535/48340281827_5e2d715fc7_q.jpg")
    }

    @Test(expected = Exception::class)
    fun `get Photo Url by id and fail`()
    {
        val response = Response.success(photoSizesError)
        runBlocking {
            `when`(imageBindingApi!!.fetchSingleImageAsync(anyString(), anyString(),anyString(), anyString(), anyLong()))
                .thenReturn(async { response })
        }

        assertEquals(0, response.body()!!.sizes.size.size)
        assertTrue(response.body()!!.sizes.size[0].source == "")
        assertTrue(response.body()!!.sizes.size.isNullOrEmpty())
        assertFalse(response.body()!!.sizes.size.isNotEmpty())
    }
}