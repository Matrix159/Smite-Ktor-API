package matrix.com.plugins

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames
import matrix.com.httpClient
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.text.Charsets.UTF_8


@Serializable
data class SessionResponse(
    @SerialName("ret_msg")
    val retMsg: String,
    @SerialName("session_id")
    val sessionId: String,
    val timestamp: String
)

fun Application.configureRouting() {

    routing {
        get("/") {
            call.respond(activeSession.toString())
        }
    }
}

fun ByteArray.toHex() = joinToString(separator = "") { byte -> "%02x".format(byte) }
