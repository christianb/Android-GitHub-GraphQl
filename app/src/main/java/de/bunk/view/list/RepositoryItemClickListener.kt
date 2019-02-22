package de.bunk.view.list

import de.bunk.domain.Repository
import de.bunk.domain.repository.PagedRepository

interface RepositoryItemClickListener {
    fun onItemClicked(pagedRepository: PagedRepository)
}