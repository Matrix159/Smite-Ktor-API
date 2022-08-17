package matrix.com

import io.ktor.client.plugins.cache.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import matrix.com.plugins.configureMonitoring
import matrix.com.plugins.configureRouting
import matrix.com.plugins.configureSerialization
import matrix.com.plugins.configureSmiteSessionPlugin

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        // TODO config from file

        configureMonitoring()
        configureSerialization()
        configureSmiteSessionPlugin()
        configureRouting()
    }.start(wait = true)
}
