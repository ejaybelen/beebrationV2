package com.example.beebration.energyharvester

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.Request

private val client = OkHttpClient()
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

suspend fun fetchEnergyHarvesterData(url: String): EnergyHarvesterData? {
    val request = Request.Builder()
        .url(url)
        .build()

    return client.newCall(request).execute().use { response ->
        if (response.isSuccessful) {
            response.body?.let { responseBody ->
                val jsonAdapter = moshi.adapter(EnergyHarvesterData::class.java)
                jsonAdapter.fromJson(responseBody.string())
            }
        } else {
            null
        }
    }
}