package de.bunk.domain.repository.list

import de.bunk.domain.GitHubDataSource
import de.bunk.domain.Repository
import de.bunk.domain.repository.PagedRepository
import io.reactivex.Single

class RepositoryListInteractor(
    private val gitHubDataSource: GitHubDataSource
) {

  fun getRepositoryList(): Single<List<PagedRepository>> = gitHubDataSource.getRepositories()
}