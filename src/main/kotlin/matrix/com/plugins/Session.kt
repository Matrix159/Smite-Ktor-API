package matrix.com.plugins

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.utils.io.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.*
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import matrix.com.httpClient
import matrix.com.util.createMD5Hash
import java.io.File
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val API_ID: String? = System.getenv("API_ID")
val API_KEY: String? = System.getenv("API_KEY")

object ZonedDateTimeTimeSerializer : KSerializer<ZonedDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("ZonedDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): ZonedDateTime =
        ZonedDateTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(value.toString())
    }

}

@Serializable
data class Session(
    val sessionId: String,
    @Serializable(ZonedDateTimeTimeSerializer::class)
    val expiresAt: ZonedDateTime
)

@Serializable
data class SessionResponse(
    @SerialName("ret_msg")
    val retMsg: String,
    @SerialName("session_id")
    val sessionId: String,
    val timestamp: String
)

var activeSession: Session? = null

val SmiteSessionPlugin = createApplicationPlugin(name = "SmiteSessionPlugin") {
    if (API_ID.isNullOrEmpty() || API_KEY.isNullOrEmpty()) {
        throw Exception("Please specify an API_ID and API_KEY")
    }
    onCall { call ->
        if (activeSession == null) {
            // try reading the session from file first
            println("Opening session file...")
            val file = File("session.json")
            if (file.exists() && file.canRead()) {
                withContext(Dispatchers.IO) {
                    try {
                        println("File exists, try to decode it and set the active session")
                        activeSession = Json.decodeFromString(file.readText())
                        println("Decoded and active session is: $activeSession")
                    } catch (ex: Exception) {
                        if (ex !is CancellationException) {
                            println("An error occurred trying to read session file.")
                            return@withContext
                        }
                        throw ex
                    }
                }
            } else {
                println("Couldn't read from file, it doesn't exist or doesn't allow us to read from it.")
            }
        }

        if (activeSession == null ||
            ZonedDateTime.now(ZoneOffset.UTC).isAfter(activeSession?.expiresAt)
        ) {
            println("Creating a session...")
            val md5Hash = createMD5Hash(endpoint = "createsession")
            val response =
                httpClient.get("https://api.smitegame.com/smiteapi.svc/createsessionJson/${API_ID}/${md5Hash.digest}/${md5Hash.utcNow}")
            if (response.status == HttpStatusCode.OK) {
                println("We received a successful session.")

                val sessionResponse = response.body<SessionResponse>()
                println("Session: $sessionResponse")

                activeSession = Session(
                    sessionId = sessionResponse.sessionId,
                    expiresAt = ZonedDateTime.parse(
                        sessionResponse.timestamp,
                        DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a").withZone(ZoneOffset.UTC)
                    ).plusMinutes(15) // Sessions are 15 minutes long, but we need to provide that offset
                )
                // Write to session file in case application restarts
                withContext(Dispatchers.IO) {
                    File("session.json").writeText(Json.encodeToString(activeSession))
                }
            } else {
                call.respond("Failed to create session")
            }
        }
    }
}

fun Application.configureSmiteSessionPlugin() {
    install(SmiteSessionPlugin)
}
