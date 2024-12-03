package org.schabi.newpipe.error

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.preference.PreferenceManager

object AppLifecycleObserver : DefaultLifecycleObserver {
    private const val KEY_IS_IN_BACKGROUND = "is_in_background"
    private var TAG = javaClass.simpleName
    private lateinit var sharedPreferences: SharedPreferences

    fun initialize(context: Context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    override fun onStart(owner: LifecycleOwner) {
        sharedPreferences.edit().putBoolean(KEY_IS_IN_BACKGROUND, false).apply()
        Log.d(TAG, "App moved to foreground")
    }

    override fun onStop(owner: LifecycleOwner) {
        sharedPreferences.edit().putBoolean(KEY_IS_IN_BACKGROUND, true).apply()
        Log.d(TAG, "App moved to background")
    }

    fun isInBackground(): Boolean {
        Log.d(
            TAG,
            "Is in background? -" +
                sharedPreferences.getBoolean(KEY_IS_IN_BACKGROUND, true)
        )
        return sharedPreferences.getBoolean(KEY_IS_IN_BACKGROUND, true)
    }
}
