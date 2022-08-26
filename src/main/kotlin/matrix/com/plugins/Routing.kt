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
            val childrenRoutes = this@routing.children
                .flatMap { constructUsableRouteList(it) }
                .filterNot { it.toString() == "/" }
                .map { it.toString() }
            
            call.respond(childrenRoutes)
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
        route("/hirezserverstatus") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "gethirezserverstatus")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get("https://api.smitegame.com/smiteapi.svc/gethirezserverstatusJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}")
                            .body()
                    )
                )
            }
        }
        route("/patchinfo") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getpatchinfo")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get("https://api.smitegame.com/smiteapi.svc/getpatchinfoJson/${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}")
                            .body()
                    )
                )
            }
        }

        route("/godleaderboard/{godId}") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getgodleaderboard")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get(
                            "https://api.smitegame.com/smiteapi.svc/getgodleaderboardJson/" +
                                    "${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/${call.parameters["godId"]}/451"
                        ).body()
                    )
                )
            }
        }

        route("/godrecommendeditems/{godId}") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getgodrecommendeditems")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get(
                            "https://api.smitegame.com/smiteapi.svc/getgodrecommendeditemsJson/" +
                                    "${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/${call.parameters["godId"]}/1"
                        ).body()
                    )
                )
            }
        }
        route("/godaltabilities") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getgodaltabilities")
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get(
                            "https://api.smitegame.com/smiteapi.svc/getgodaltabilitiesJson/" +
                                    "${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}"
                        ).body()
                    )
                )
            }
        }

        route("/getplayer/{player}/{portalId?}") {
            get {
                val hash: MD5Hash = createMD5Hash(endpoint = "getplayer")
                val player = call.parameters["player"]
                val portalId = call.parameters["portalId"]
                var urlToFetch = "https://api.smitegame.com/smiteapi.svc/getplayerJson/" +
                        "${API_ID}/${hash.digest}/${activeSession?.sessionId}/${hash.utcNow}/${player}"
                if (portalId?.isNotEmpty() == true) {
                    urlToFetch += "/${portalId}"
                }
                call.respond(
                    Json.parseToJsonElement(
                        httpClient.get(urlToFetch).body()
                    )
                )
            }
        }
    }
}

fun constructUsableRouteList(root: Route): List<Route> {
    if (root.children.isEmpty()) {
        return root.parent?.let { listOf(it) } ?: emptyList()
    }
    return root.children.flatMap { constructUsableRouteList(it) }
}

