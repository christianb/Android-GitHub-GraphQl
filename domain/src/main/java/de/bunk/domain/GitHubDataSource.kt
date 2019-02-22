package de.bunk.domain

import de.bunk.domain.repository.PagedRepository
import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(): Single<List<PagedRepository>>

    fun getRepository(owner: String, repoName: String): Single<Repository>
}