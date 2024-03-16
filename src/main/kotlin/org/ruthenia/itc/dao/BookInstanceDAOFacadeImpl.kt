package org.ruthenia.itc.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.ruthenia.itc.dao.DatabaseSingleton.dbQuery
import org.ruthenia.itc.models.*
import java.time.LocalDate

class BookInstanceDAOFacadeImpl : BookInstanceDAOFacade {

    override suspend fun all(): List<BookInstance> = dbQuery {
        BookInstances.selectAll().map { resultRowToBookInstance(it) }
    }

    override suspend fun bookInstance(id: Long): BookInstance? = dbQuery {
        BookInstances.select { BookInstances.id eq id }.map { resultRowToBookInstance(it) }.singleOrNull()
    }

    override suspend fun create(
        bookId: Long,
        imprint: String,
        isbn: String,
        status: BookInstanceStatus,
        dueBack: LocalDate?
    ): BookInstance? = dbQuery {
        val insertStatement = BookInstances.insert {
            it[BookInstances.bookId] = bookId
            it[BookInstances.imprint] = imprint
            it[BookInstances.isbn] = isbn
            it[BookInstances.status] = status
            it[BookInstances.dueBack] = dueBack
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToBookInstance)
    }

    override suspend fun update(
        id: Long,
        bookId: Long,
        imprint: String,
        isbn: String,
        status: BookInstanceStatus,
        dueBack: LocalDate?
    ): Boolean = dbQuery {
        BookInstances.update({ BookInstances.id eq id }) {
            it[BookInstances.bookId] = bookId
            it[BookInstances.imprint] = imprint
            it[BookInstances.isbn] = isbn
            it[BookInstances.status] = status
            it[BookInstances.dueBack] = dueBack
        } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        BookInstances.deleteWhere { BookInstances.id eq id } > 0
    }

    private fun resultRowToBookInstance(row: ResultRow) = BookInstance(
        id = row[BookInstances.id],
        bookId = row[BookInstances.bookId],
        imprint = row[BookInstances.imprint],
        isbn = row[BookInstances.isbn],
        status = row[BookInstances.status],
        dueBack = row[BookInstances.dueBack]
    )
}

val bookInstanceDao: BookInstanceDAOFacade by lazy { BookInstanceDAOFacadeImpl() }