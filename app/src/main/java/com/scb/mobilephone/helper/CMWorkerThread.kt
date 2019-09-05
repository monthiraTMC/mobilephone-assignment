package com.scb.mobilephone.helper

import android.os.Handler
import android.os.HandlerThread

class CMWorkerThread(threadName: String) : HandlerThread(threadName) {
    private lateinit var mWorkerHandler: Handler
    override fun onLooperPrepared() {
        super.onLooperPrepared()
        mWorkerHandler = Handler(looper)
    }

    fun postTask(task: Runnable) {
        mWorkerHandler = Handler(looper)
        mWorkerHandler.post(task)
    }
}