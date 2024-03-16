package org.ruthenia.itc.models

import org.jetbrains.exposed.sql.Table

data class Book(
    val id: Long,
    val title: String,
    val authorId: Long,
    val summary: String,
    val genreId: Long
)

object Books : Table() {
    val id = long("id").autoIncrement()
    val title = varchar("title", 100)
    val authorId = reference("author", Authors.id)
    val summary = text("summary")
    val genreId = reference("genre", Genres.id)
    override val primaryKey = PrimaryKey(id)
}
