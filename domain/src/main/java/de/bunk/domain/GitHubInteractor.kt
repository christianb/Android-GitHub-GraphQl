package de.bunk.domain

import io.reactivex.Single

class GitHubInteractor(
    private val gitHubDataSource: GitHubDataSource
) {

  fun getRepositoryList(): Single<List<Repository>> = gitHubDataSource.getRepositories()
}