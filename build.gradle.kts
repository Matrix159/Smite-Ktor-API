val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project


plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.8"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

group = "matrix.com"
version = "0.0.1"
application {
    mainClass.set("matrix.com.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-sessions-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // ktor client
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("io.ktor:ktor-client-cio:$ktor_version")

    // cache headers
    implementation("io.ktor:ktor-server-caching-headers:$ktor_version")

    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

// For heroku, make an executable for it to run
//tasks {
//    create("stage").dependsOn("installDist")
//}
