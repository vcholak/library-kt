package org.ruthenia.itc.dao

import org.ruthenia.itc.models.Author
import java.time.LocalDate

interface AuthorDAOFacade {
    suspend fun all(): List<Author>
    suspend fun author(id: Long): Author?
    suspend fun create(firstName: String, familyName: String, birthDate: LocalDate, deathDate: LocalDate?, lifeSpan: String): Author?
    suspend fun update(id: Long, firstName: String, familyName: String, birthDate: LocalDate, deathDate: LocalDate?, lifeSpan: String): Boolean
    suspend fun delete(id: Long): Boolean
}