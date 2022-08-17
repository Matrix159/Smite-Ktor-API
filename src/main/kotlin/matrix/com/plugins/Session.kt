package matrix.com.plugins

import io.github.cdimascio.dotenv.dotenv
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
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val dotenv = dotenv()
val API_ID: String? = dotenv["API_ID"]
val API_KEY: String? = dotenv["API_KEY"]

object LocalDateTimeSerializer : KSerializer<LocalDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("LocalDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDateTime =
        LocalDateTime.parse(decoder.decodeString())

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.toString())
    }

}

@Serializable
data class Session(
    val sessionId: String,
    @Serializable(LocalDateTimeSerializer::class)
    val expiresAt: LocalDateTime
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

val SmiteSessionPlugin = createApplicationPlugin(name = "SimplePlugin") {
    if (API_ID.isNullOrEmpty() || API_KEY.isNullOrEmpty()) {
        throw Exception("Please specify an API_ID and API_KEY")
    }
    onCall { call ->
        if (activeSession == null) {
            // try reading the session from file first
            val file = File("session.json")
            if (file.exists() && file.canRead()) {
                withContext(Dispatchers.IO) {
                    try {
                        activeSession = Json.decodeFromString(file.readText())
                    } catch (ex: Exception) {
                        if (ex !is CancellationException) {
                            println("An error occurred trying to read session file.")
                        }
                    }
                }
            }
        }
        if (activeSession == null || activeSession?.expiresAt?.isBefore(LocalDateTime.now()) == true) {
            val md5Hash = createMD5Hash(endpoint = "createsession")
            val response =
                httpClient.get("https://api.smitegame.com/smiteapi.svc/createsessionJson/${API_ID}/${md5Hash.digest}/${md5Hash.utcNow}")
            if (response.status == HttpStatusCode.OK) {
                val sessionResponse = response.body<SessionResponse>()
                println(sessionResponse.timestamp)

                activeSession = Session(
                    sessionId = sessionResponse.sessionId,
                    expiresAt = LocalDateTime.parse(
                        sessionResponse.timestamp,
                        DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a")
                    )
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
