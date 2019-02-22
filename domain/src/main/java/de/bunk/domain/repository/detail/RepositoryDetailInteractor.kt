package de.bunk.domain.repository.detail

import de.bunk.domain.GitHubDataSource

class RepositoryDetailInteractor(
    private val gitHubDataSource: GitHubDataSource
) {
    fun getRepositoryDetail(owner: String, repoName: String) = gitHubDataSource.getRepository(owner, repoName)
}