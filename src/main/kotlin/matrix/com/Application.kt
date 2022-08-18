package matrix.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import matrix.com.plugins.*

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, host = "0.0.0.0") {
        // TODO config from file

        configureMonitoring()
        configureSerialization()
        configureCaching()
        configureSmiteSessionPlugin()
        configureRouting()
    }.start(wait = true)
}
