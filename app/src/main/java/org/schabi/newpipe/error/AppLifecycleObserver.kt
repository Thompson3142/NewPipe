package org.schabi.newpipe.error

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.util.concurrent.atomic.AtomicLong

object AppLifecycleObserver : DefaultLifecycleObserver {
    private var isInBackground = false
    private val lastBackgroundTimestamp = AtomicLong(0)
    private var TAG = javaClass.simpleName

    override fun onStart(owner: LifecycleOwner) {
        isInBackground = false
        Log.d(TAG, "App moved to foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        isInBackground = true
        lastBackgroundTimestamp.set(System.currentTimeMillis())
        Log.d(TAG, "App moved to background")
    }

    fun isAppInBackground(): Boolean = isInBackground

    /**
     * @return the elapsed time since the app moved to the background or 0 if it is in foreground
     */
    fun getTimeSinceLastBackground(): Long {
        return if (isInBackground) System.currentTimeMillis() - lastBackgroundTimestamp.get() else 0
    }
}
