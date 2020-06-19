package com.app.util

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    /**
     * This is a scope for all co-routines launched by [BaseViewModel]
     * that will be dispatched in a Pool of Thread
     */
    protected val ioScope = CoroutineScope(Dispatchers.IO)

    /**
     * Cancel all co-routines when the ViewModel is cleared
     */
    override fun onCleared() {
        super.onCleared()
        ioScope.coroutineContext.cancel()
    }
}