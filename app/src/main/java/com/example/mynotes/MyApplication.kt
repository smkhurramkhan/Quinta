package com.example.mynotes

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.mynotes.utils.DbHelper
import org.jetbrains.annotations.NotNull
import timber.log.Timber

class MyApplication : Application() {
    val hTag = "khurramTag %s"

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        initTimber()

        DbHelper.getInstance(this)

    }


    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    @NotNull message: String,
                    t: Throwable?
                ) {
                    super.log(priority, String.format(hTag, tag), message, t)
                }
            })
        }
    }
}