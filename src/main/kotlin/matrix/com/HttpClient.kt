package matrix.com

import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

const val TWO_MINUTE_TIMEOUT: Long = 2 * 60 * 1000

val httpClient = HttpClient {
    expectSuccess = true

    install(HttpTimeout) {
        requestTimeoutMillis = TWO_MINUTE_TIMEOUT
        connectTimeoutMillis = TWO_MINUTE_TIMEOUT
        socketTimeoutMillis = TWO_MINUTE_TIMEOUT
    }

    install(ContentNegotiation) {
        json()
    }

    install(HttpCache)
}
