package com.sandeep.kmpcore.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel(
    private val globalExceptionHandler: CoroutineExceptionHandler,
) : ViewModel() {

    protected open fun onError(throwable: Throwable) {}

    private val combinedHandler = CoroutineExceptionHandler { ctx, throwable ->
        onError(throwable)
        globalExceptionHandler.handleException(ctx, throwable)
    }

    protected fun launchSafe(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend CoroutineScope.() -> Unit,
    ): Job = viewModelScope.launch(context + combinedHandler, block = block)
}
