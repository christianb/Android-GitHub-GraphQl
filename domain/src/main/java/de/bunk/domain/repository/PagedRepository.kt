package de.bunk.domain.repository

import de.bunk.domain.Repository

data class PagedRepository(
    val repository: Repository,
    val cursor: String
)