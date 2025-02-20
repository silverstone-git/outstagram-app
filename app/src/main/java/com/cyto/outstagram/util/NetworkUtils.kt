package com.cyto.outstagram.util

import android.util.Log
import com.cyto.outstagram.ui.dashboard.model.UserPublic
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import java.io.IOException

object NetworkUtils {

    private const val TAG = "NetworkUtils"
    private const val BASE_URL = "http://localhost:8000"

    private val client = OkHttpClient()

    data class LoginResponse(
        @SerializedName("access_token") val jwt: String,
        @SerializedName("token_type") val tokenType: String,
        // string for now, to store it in shared preferences
        val user: String)

    fun login(email: String, password: String): LoginResponse? {
        val url = "$BASE_URL/login"

        val formBody: RequestBody = FormBody.Builder()
            .add("email", email)
            .add("password", password)
            .build()

        val request: Request = Request.Builder()
            .url(url)
            .post(formBody)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) {
                    Log.e(TAG, "Login failed: ${response.code} - ${response.message}")
                    return null
                }

                val responseBody = response.body?.string()
                Log.d(TAG, "Login response: $responseBody")

                if (responseBody != null) {
                    val gson = Gson()
                    val loginResponse = gson.fromJson(responseBody, LoginResponse::class.java)
                    return loginResponse
                } else {
                    Log.e(TAG, "Empty response body")
                    return null
                }
            }
        } catch (e: IOException) {
            Log.e(TAG, "Network error: ${e.message}")
            return null
        }
    }
}