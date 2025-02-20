package com.cyto.outstagram.model

data class JwtPayload(
    val sub: String,
    val exp: Long,
    val iat: Long,
    val username: String,
)