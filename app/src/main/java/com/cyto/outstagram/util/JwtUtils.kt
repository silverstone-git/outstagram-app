package com.cyto.outstagram.util

import android.util.Log
import com.auth0.android.jwt.JWT
import com.auth0.android.jwt.DecodeException
import com.cyto.outstagram.model.JwtPayload

object JwtUtils {

    private const val TAG = "JwtUtils"

    fun isValidJwt(jwtString: String?): Boolean {
        if (jwtString.isNullOrBlank()) {
            Log.d(TAG, "JWT is null or blank")
            return false
        }

        return try {
            val jwt: JWT = JWT(jwtString)
            val isExpired = jwt.isExpired(100)
            if (isExpired) {
                Log.d(TAG, "JWT is expired")
            }
            !isExpired
        } catch (e: DecodeException) {
            Log.e(TAG, "Error decoding JWT: ${e.message}")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Error validating JWT: ${e.message}")
            false
        }
    }

    fun getPayload(jwtString: String): JwtPayload? {
        return try {
            val jwt: JWT = JWT(jwtString)
            val sub = jwt.getClaim("sub").asString()
            val exp = jwt.getClaim("exp").asLong()
            val iat = jwt.getClaim("iat").asLong()
            val username = jwt.getClaim("username").asString()
            JwtPayload(sub = sub ?: "", exp = exp ?: 0, iat = iat ?: 0, username = username ?: "")
        } catch (e: DecodeException) {
            Log.e(TAG, "Error decoding JWT: ${e.message}")
            null
        }
    }
}