package org.ruthenia.itc.dao

import org.ruthenia.itc.models.Genre

interface GenreDAOFacade {
    suspend fun all(): List<Genre>
    suspend fun genre(id: Long): Genre?
    suspend fun create(name: String): Genre?
    suspend fun update(id: Long, name: String): Boolean
    suspend fun delete(id: Long): Boolean
}