package com.app.util

import org.mockito.Mockito
 
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)