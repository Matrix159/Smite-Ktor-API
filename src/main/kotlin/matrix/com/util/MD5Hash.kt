package matrix.com.util

import matrix.com.plugins.API_ID
import matrix.com.plugins.API_KEY
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

data class MD5Hash(
    val utcNow: String,
    val digest: String
)

fun utcNowForHash(): String =
    DateTimeFormatter
        .ofPattern("yyyyMMddHHmmss")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())

fun createMD5Hash(utcNow: String = utcNowForHash(), endpoint: String): MD5Hash {
    return MD5Hash(
        utcNow = utcNow,
        digest = MessageDigest.getInstance("MD5")
            .digest("${API_ID}${endpoint}$API_KEY${utcNow}".toByteArray(Charsets.UTF_8))
            .toHex()
    )
}
