package com.felfeit.androidmvcexample.view.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseController {

    protected val scope = CoroutineScope(Dispatchers.Main + Job())

    open fun onDestroy() {
        scope.cancel()
    }
}