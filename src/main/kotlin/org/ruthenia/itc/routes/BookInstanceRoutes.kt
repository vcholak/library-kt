package org.ruthenia.itc.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.util.*
import org.ruthenia.itc.dao.bookInstanceDao
import org.ruthenia.itc.models.BookInstance

fun Route.bookInstanceRoutes() {
    route("/copies") {
        get {
            call.respond(bookInstanceDao.all())
        }
        get("{id}") {
            val id = call.parameters.getOrFail<Long>("id").toLong()
            val bookInstance = bookInstanceDao.bookInstance(id)
            if (bookInstance == null) {
                return@get call.respondText("BookInstance with id $id not found", status = HttpStatusCode.NotFound)
            }
            call.respond(mapOf("bookInstance" to bookInstance))
        }
        post {
            val payload = call.receive<BookInstance>()
            val bookInstance = bookInstanceDao.create(payload.bookId, payload.imprint, payload.isbn, payload.status, payload.dueBack)
            call.respond(mapOf("bookInstance" to bookInstance))
        }
        put("{id}") {
            val id = call.parameters["id"] ?: return@put call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val payload = call.receive<BookInstance>()
            val resp = bookInstanceDao.update(id.toLong(), payload.bookId, payload.imprint, payload.isbn, payload.status, payload.dueBack)
            call.respond(resp)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respondText("Missing id", status = HttpStatusCode.BadRequest)
            val resp = bookInstanceDao.delete(id.toLong())
            call.respond(resp)
        }
    }
}