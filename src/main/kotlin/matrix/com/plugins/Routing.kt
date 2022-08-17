package matrix.com.plugins

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.json.Json
import matrix.com.httpClient
import matrix.com.models.GodInformation
import matrix.com.models.GodSkin
import matrix.com.models.Item
import matrix.com.util.MD5Hash
import matrix.com.util.createMD5Hash

fun Application.configureRouting() {
    routing {
        get("/") {
            activeSession?.let {
                call.respond(activeSession!!)
            }
        }
        route("/gods") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getgods")
                call.respond(
                    httpClient.get("https://api.smitegame.com/smiteapi.svc/getgodsJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/1")
                        .body<List<GodInformation>>()
                )
            }
        }
        route("/godskins") {
            get("{godId}") {
                val hash: MD5Hash = createMD5Hash(endpoint = "getgodskins")
                call.respond(
                    httpClient.get("https://api.smitegame.com/smiteapi.svc/getgodskinsJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/${call.parameters["godId"]}/1")
                        .body<List<GodSkin>>()
                )
            }
        }
        route("/items") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getitems")
                call.respond(
                    httpClient.get("https://api.smitegame.com/smiteapi.svc/getitemsJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/1")
                        .body<List<Item>>()
                )
            }
        }
        route("/datausage") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getdataused")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get("https://api.smitegame.com/smiteapi.svc/getdatausedJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}")
                            .body()
                    )
                )
            }
        }
    }
}


