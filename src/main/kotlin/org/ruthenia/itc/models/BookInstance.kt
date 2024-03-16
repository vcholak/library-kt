package org.ruthenia.itc.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

data class BookInstance(
    val id: Long,
    val bookId: Long,
    val imprint: String,
    val isbn: String,
    val status: BookInstanceStatus,
    val dueBack: LocalDate? = null
)

enum class BookInstanceStatus {
    NOT_AVAILABLE,
    ON_ORDER,
    IN_TRANSIT,
    ON_HOLD,
    ON_LOAN,
    AVAILABLE
}

object BookInstances : Table() {
    val id = long("id").autoIncrement()
    val bookId = reference("book", Books.id)
    val imprint = varchar("imprint", 255)
    val isbn = varchar("isbn", 255)
    val status = enumerationByName("status", 255, BookInstanceStatus::class)
    val dueBack = date("due_back").nullable()
    override val primaryKey = PrimaryKey(id)
}