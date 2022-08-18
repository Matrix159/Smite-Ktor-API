package matrix.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import java.time.ZoneOffset
import java.time.ZonedDateTime

private const val MAX_REQUESTS_PER_HOUR = 312
private var requestsLimitedThisHour = 0
private var rateExpirationTime = ZonedDateTime.now(ZoneOffset.UTC).plusHours(1)
val RateLimiter = createApplicationPlugin(name = "RateLimiter") {
    onCall { call ->
        if (ZonedDateTime.now(ZoneOffset.UTC) >= rateExpirationTime) {
            rateExpirationTime = ZonedDateTime.now(ZoneOffset.UTC).plusHours(1)
            println("New expiration time: $rateExpirationTime")
            requestsLimitedThisHour = 0
            return@onCall
        }
        requestsLimitedThisHour += 1
        if (requestsLimitedThisHour > MAX_REQUESTS_PER_HOUR) {
            call.respond(HttpStatusCode.TooManyRequests, "Too many requests")
        }
    }
}

fun Application.configureRateLimiter() {
    install(RateLimiter)
}
