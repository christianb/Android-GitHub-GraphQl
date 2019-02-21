package de.bunk.domain

import io.reactivex.Single

interface GitHubDataSource {
    fun getRepositories(): Single<List<Repository>>
}