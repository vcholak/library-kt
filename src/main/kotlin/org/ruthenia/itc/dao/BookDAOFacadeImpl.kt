package org.ruthenia.itc.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.ruthenia.itc.dao.DatabaseSingleton.dbQuery
import org.ruthenia.itc.models.Book
import org.ruthenia.itc.models.Books

class BookDAOFacadeImpl : BookDAOFacade {

    override suspend fun all(): List<Book> = dbQuery {
        Books.selectAll().map { resultRowToBook(it) }
    }

    override suspend fun book(id: Long): Book? = dbQuery {
        Books.select { Books.id eq id }.map { resultRowToBook(it) }.singleOrNull()
    }

    override suspend fun create(title: String, authorId: Long, summary: String, genreId: Long): Book? = dbQuery {
        val insertStatement = Books.insert {
            it[Books.title] = title
            it[Books.authorId] = authorId
            it[Books.summary] = summary
            it[Books.genreId] = genreId
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToBook)
    }

    override suspend fun update(id: Long, title: String, authorId: Long, summary: String, genreId: Long): Boolean = dbQuery {
        Books.update({ Books.id eq id }) {
            it[Books.title] = title
            it[Books.authorId] = authorId
            it[Books.summary] = summary
            it[Books.genreId] = genreId
        } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Books.deleteWhere { Books.id eq id } > 0
    }

    private fun resultRowToBook(row: ResultRow) = Book(
        id = row[Books.id],
        title = row[Books.title],
        authorId = row[Books.authorId],
        summary = row[Books.summary],
        genreId = row[Books.genreId]
    )
}

val bookDao: BookDAOFacade by lazy { BookDAOFacadeImpl() }