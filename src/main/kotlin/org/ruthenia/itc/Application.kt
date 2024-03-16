package org.ruthenia.itc

import io.ktor.server.application.*
import io.ktor.server.netty.*
import org.ruthenia.itc.dao.DatabaseSingleton.configureDatabase
import org.ruthenia.itc.plugins.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    configureDatabase()
    configureAdministration()
    configureSerialization()
    configureMonitoring()
    configureHTTP()
    configureRouting()
}