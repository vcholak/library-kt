package org.ruthenia.itc.dao

import org.ruthenia.itc.models.BookInstance
import org.ruthenia.itc.models.BookInstanceStatus
import java.time.LocalDate

interface BookInstanceDAOFacade {
    suspend fun all(): List<BookInstance>
    suspend fun bookInstance(id: Long): BookInstance?
    suspend fun create(bookId: Long, imprint: String, isbn: String, status: BookInstanceStatus, dueBack: LocalDate?): BookInstance?
    suspend fun update(id: Long, bookId: Long, imprint: String, isbn: String, status: BookInstanceStatus, dueBack: LocalDate?): Boolean
    suspend fun delete(id: Long): Boolean
}