package com.example.livesamp.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Standard Base View Model
 */
abstract class BaseViewModel : ViewModel() {
    var isPopBackStack = false
    var errorMessageObs = MutableLiveData<String>()
    var loadingObs = MutableLiveData<Boolean>()

    /**
     * Show error message
     *
     * @param msg to show string message
     */
    protected fun showErrorMessage(msg: String) {
        errorMessageObs.postValue(msg)
    }

    /**
     * Show loading
     */
    protected fun showLoading() {
        loadingObs.postValue(true)
    }

    /**
     * Hide loading
     */
    protected fun hideLoading() {
        loadingObs.postValue(false)
    }

    protected fun launchCoroutineScope(
        block: suspend CoroutineScope.() -> Unit,
        onError: ((Throwable) -> Unit)? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        kotlin.runCatching {
            block.invoke(this)
        }.onFailure {
            onError?.invoke(it)
        }
    }
}
