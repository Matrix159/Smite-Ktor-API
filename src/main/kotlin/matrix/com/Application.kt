package matrix.com

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import matrix.com.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0") {
        configureMonitoring()
        configureSerialization()
        configureSmiteSessionPlugin()
        configureRouting()
    }.start(wait = true)
}
