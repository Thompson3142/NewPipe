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
    private lateinit var editor: SharedPreferences.Editor

    // Only call this once on startup
    fun initialize(context: Context) {
        if (!this::sharedPreferences.isInitialized) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
            editor = sharedPreferences.edit()
        }
    }

    override fun onStart(owner: LifecycleOwner) {
        editor.putBoolean(KEY_IS_IN_BACKGROUND, false).commit()
        Log.d(TAG, "App moved to foreground")
    }

    override fun onPause(owner: LifecycleOwner) {
        editor.putBoolean(KEY_IS_IN_BACKGROUND, true).commit()
        Log.d(TAG, "App moved to background")
    }


    /**
     * Returns if the app is currently in the background
     * or in case of a crash the state when the crash happened
     */
    fun isInBackground(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_IN_BACKGROUND, true)
    }
}
