package com.iranvpnpro

import okhttp3.OkHttpClient
import okhttp3.Request

object SubscriptionLoader {

    private val client = OkHttpClient()

    fun loadSubscription(): String? {
        val url =
            "https://raw.githubusercontent.com/erfanyos/cat_tunnel/main/sub.txt"

        return try {
            val request = Request.Builder()
                .url(url)
                .build()
            val response = client.newCall(request).execute()
            response.body?.string()
        } catch (e: Exception) {
            null
        }
    }
}
