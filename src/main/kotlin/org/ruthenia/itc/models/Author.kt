package org.ruthenia.itc.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import java.time.LocalDate

data class Author(
    val id: Long,
    val firstName: String,
    val familyName: String,
    val birthDate: LocalDate,
    val deathDate: LocalDate? = null,
    val lifeSpan: String,
    val books: List<Book> = emptyList()
)

object Authors : Table() {
    val id = long("id").autoIncrement()
    val firstName = varchar("first_name", 255)
    val familyName = varchar("family_name", 255)
    val birthDate = date("birth_date")
    val deathDate = date("death_date").nullable()
    val lifeSpan = varchar("life_span", 255)
    override val primaryKey = PrimaryKey(id)
}