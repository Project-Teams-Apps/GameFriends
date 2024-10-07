package com.gamefriends

import android.app.Application
import android.os.Build
import dagger.hilt.android.HiltAndroidApp
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient


@HiltAndroidApp
open class MyApplication : Application()