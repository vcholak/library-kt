package org.ruthenia.itc.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ruthenia.itc.dao.authorDao
import org.ruthenia.itc.models.Author

fun Route.authorRoutes() {
    route("/authors") {
        get {
            call.respond(authorDao.all())
        }
        get("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val author = authorDao.author(id)
            if (author == null) {
                return@get call.respondText("Author with id $id not found", status = HttpStatusCode.NotFound)
            }
            call.respond(mapOf("author" to author))
        }
        post {
            val payload = call.receive<Author>()
            val author = authorDao.create(payload.firstName, payload.familyName, payload.birthDate, payload.deathDate, payload.lifeSpan)
            call.respond(mapOf("author" to author))
        }
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val payload = call.receive<Author>()
            val resp = authorDao.update(id.toLong(), payload.firstName, payload.familyName, payload.birthDate, payload.deathDate, payload.lifeSpan)
            call.respond(resp)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val resp = authorDao.delete(id.toLong())
            call.respond(resp)
        }
    }
}