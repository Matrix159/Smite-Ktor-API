package matrix.com.plugins

import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cachingheaders.*

fun Application.configureCaching() {
    install(CachingHeaders) {
        options { _, _ ->
            CachingOptions(CacheControl.MaxAge(maxAgeSeconds = 300))
        }
    }
}
