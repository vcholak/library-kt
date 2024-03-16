package org.ruthenia.itc.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.ruthenia.itc.dao.DatabaseSingleton.dbQuery
import org.ruthenia.itc.models.Author
import org.ruthenia.itc.models.Authors
import java.time.LocalDate

class AuthorDAOFacadeImpl : AuthorDAOFacade {

    override suspend fun all(): List<Author> = dbQuery {
        Authors.selectAll().map { resultRowToAuthor(it) }
    }

    private fun resultRowToAuthor(row: ResultRow) = Author(
        id = row[Authors.id],
        firstName = row[Authors.firstName],
        familyName = row[Authors.familyName],
        birthDate = row[Authors.birthDate],
        deathDate = row[Authors.deathDate],
        lifeSpan = row[Authors.lifeSpan],
    )

    override suspend fun author(id: Long): Author? = dbQuery {
        Authors.select { Authors.id eq id }.map { resultRowToAuthor(it) }.singleOrNull()
    }

    override suspend fun create(firstName: String, familyName: String, birthDate: LocalDate, deathDate: LocalDate?, lifeSpan: String): Author? = dbQuery {
        val insertStatement = Authors.insert {
            it[Authors.firstName] = firstName
            it[Authors.familyName] = familyName
            it[Authors.birthDate] = birthDate
            it[Authors.deathDate] = deathDate
            it[Authors.lifeSpan] = lifeSpan
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToAuthor)
    }

    override suspend fun update(id: Long, firstName: String, familyName: String, birthDate: LocalDate, deathDate: LocalDate?, lifeSpan: String): Boolean = dbQuery {
        Authors.update({ Authors.id eq id }) {
            it[Authors.firstName] = firstName
            it[Authors.familyName] = familyName
            it[Authors.birthDate] = birthDate
            it[Authors.deathDate] = deathDate
            it[Authors.lifeSpan] = lifeSpan
        } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Authors.deleteWhere { Authors.id eq id } > 0
    }
}

val authorDao: AuthorDAOFacade by lazy { AuthorDAOFacadeImpl() }