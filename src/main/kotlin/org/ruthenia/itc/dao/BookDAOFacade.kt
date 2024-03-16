package org.ruthenia.itc.dao

import org.ruthenia.itc.models.Book

interface BookDAOFacade {
    suspend fun all(): List<Book>
    suspend fun book(id: Long): Book?
    suspend fun create(title: String, authorId: Long, summary: String, genreId: Long): Book?
    suspend fun update(id: Long, title: String, authorId: Long, summary: String, genreId: Long): Boolean
    suspend fun delete(id: Long): Boolean
}