package matrix.com.plugins

import io.github.cdimascio.dotenv.dotenv
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import matrix.com.httpClient
import java.security.MessageDigest
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

val dotenv = dotenv()
val API_ID: String? = dotenv["API_ID"]
val API_KEY: String? = dotenv["API_KEY"]


data class Session(val sessionId: String, val expiresAt: String)

var activeSession: Session? = null

val SmiteSessionPlugin = createApplicationPlugin(name = "SimplePlugin") {
    if (API_ID.isNullOrEmpty() || API_KEY.isNullOrEmpty()) {
        throw Exception("Please specify an API_ID and API_KEY")
    }
    onCall { call ->
        // TODO check for expired session
        if (activeSession == null) {
            val utcNow: String = DateTimeFormatter
                .ofPattern("yyyyMMddHHmmss")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
            val digest =
                MessageDigest.getInstance("MD5")
                    .digest("${API_ID}createsession${API_KEY}${utcNow}".toByteArray(Charsets.UTF_8))
                    .toHex()
            val response =
                httpClient.get("https://api.smitegame.com/smiteapi.svc/createsessionJson/${API_ID}/${digest}/${utcNow}")
            if (response.status == HttpStatusCode.OK) {
                val sessionResponse = response.body<SessionResponse>()
                activeSession = Session(sessionId = sessionResponse.sessionId, expiresAt = sessionResponse.timestamp)
            } else {
                call.respond("Session is not valid")
            }
        }
    }
}

fun Application.configureSmiteSessionPlugin() {
    install(SmiteSessionPlugin)
}
