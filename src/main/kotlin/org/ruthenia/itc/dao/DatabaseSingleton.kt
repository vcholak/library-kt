package org.ruthenia.itc.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import org.ruthenia.itc.models.Authors
import org.ruthenia.itc.models.BookInstances
import org.ruthenia.itc.models.Books
import org.ruthenia.itc.models.Genres

object DatabaseSingleton {
    fun configureDatabase() {
        val driverClassName = "org.postgresql.Driver"
        val jdbcURL = "jdbc:postgresql://localhost:5432/postgres"
        val user = "admin"
        val password = "adminpwd"
        val database = Database.connect(jdbcURL, driverClassName, user, password)

        transaction(database) {
            SchemaUtils.create(Genres, Books, Authors, BookInstances)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}