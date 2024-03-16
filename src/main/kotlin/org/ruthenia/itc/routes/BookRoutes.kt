package org.ruthenia.itc.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ruthenia.itc.dao.bookDao
import org.ruthenia.itc.models.Book

fun Route.bookRoutes() {
    route("/books") {
        get {
            call.respond(bookDao.all())
        }
        get("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val book = bookDao.book(id)
            if (book == null) {
                return@get call.respondText("Book with id $id not found", status = HttpStatusCode.NotFound)
            }
            call.respond(mapOf("book" to book))
        }
        post {
            val payload = call.receive<Book>()
            val book = bookDao.create(payload.title, payload.authorId, payload.summary, payload.genreId)
            call.respond(mapOf("book" to book))
        }
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val payload = call.receive<Book>()
            val resp = bookDao.update(id.toLong(), payload.title, payload.authorId, payload.summary, payload.genreId)
            call.respond(resp)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val resp = bookDao.delete(id.toLong())
            call.respond(resp)
        }
    }
}