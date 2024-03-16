package org.ruthenia.itc.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.ruthenia.itc.dao.DatabaseSingleton.dbQuery
import org.ruthenia.itc.models.Genre
import org.ruthenia.itc.models.Genres

class GenreDAOFacadeImpl : GenreDAOFacade {

    override suspend fun all(): List<Genre> = dbQuery {
        Genres.selectAll().map { resultRowToGenre(it) }
    }

    override suspend fun genre(id: Long): Genre? = dbQuery {
        Genres.select { Genres.id eq id }.map { resultRowToGenre(it) }.singleOrNull()
    }

    override suspend fun create(name: String): Genre? = dbQuery {
        val insertStatement = Genres.insert {
            it[Genres.name] = name
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToGenre)
    }

    override suspend fun update(id: Long, name: String): Boolean = dbQuery {
        Genres.update({ Genres.id eq id }) {
            it[Genres.name] = name
        } > 0
    }

    override suspend fun delete(id: Long): Boolean = dbQuery {
        Genres.deleteWhere { Genres.id eq id } > 0
    }

    private fun resultRowToGenre(row: ResultRow) = Genre(
        id = row[Genres.id],
        name = row[Genres.name]
    )
}

val genreDao: GenreDAOFacade by lazy { GenreDAOFacadeImpl() }
