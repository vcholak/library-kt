package org.ruthenia.itc.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ruthenia.itc.dao.genreDao
import org.ruthenia.itc.models.Genre

fun Route.genreRoutes() {
    route("/genres") {
        get {
            call.respond(genreDao.all())
        }
        get("{id?}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val genre = genreDao.genre(id)
            if (genre == null) {
                return@get call.respondText("Genre with id $id not found", status = HttpStatusCode.NotFound)
            }
            call.respond(mapOf("genre" to genre))
        }
        post {
            val name = call.receive<Genre>().name
            val genre = genreDao.create(name)
            call.respond(mapOf("genre" to genre))
        }
        put("{id?}") {
            val id = call.parameters["id"] ?: return@put call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val genre = call.receive<Genre>()
            val resp = genreDao.update(id.toLong(), genre.name)
            call.respond(resp)
        }
        delete("{id?}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val resp = genreDao.delete(id.toLong())
            call.respond(resp)
        }
    }
}