package org.ruthenia.itc.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.ruthenia.itc.routes.authorRoutes
import org.ruthenia.itc.routes.bookInstanceRoutes
import org.ruthenia.itc.routes.bookRoutes
import org.ruthenia.itc.routes.genreRoutes

fun Application.configureRouting() {
    routing {
        genreRoutes()
        authorRoutes()
        bookRoutes()
        bookInstanceRoutes()
    }
}
