package org.ruthenia.itc.models

import org.jetbrains.exposed.sql.Table

data class Genre(val id: Long, val name: String)

object Genres : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()
    override val primaryKey = PrimaryKey(id)
}