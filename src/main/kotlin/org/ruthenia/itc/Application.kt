package org.ruthenia.itc

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.ruthenia.itc.dao.DatabaseSingleton.configureDatabase
import org.ruthenia.itc.plugins.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureDatabase()
    configureAdministration()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}