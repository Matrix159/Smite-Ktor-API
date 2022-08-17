package matrix.com

import io.ktor.client.*
import io.ktor.client.plugins.cache.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }

    install(HttpCache)
}
