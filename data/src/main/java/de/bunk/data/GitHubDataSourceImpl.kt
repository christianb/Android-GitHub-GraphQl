package de.bunk.data

import GetRepositoryDetailQuery
import SearchMostStarQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import de.bunk.common.SubscribeOnScheduler
import de.bunk.domain.GitHubDataSource
import de.bunk.domain.Repository
import de.bunk.domain.repository.PagedRepository
import io.reactivex.Observable
import io.reactivex.Single

class GitHubDataSourceImpl(
    private val subscribeOnScheduler: SubscribeOnScheduler,
    apiKey: ApiKey
) : GitHubDataSource {

    private val apolloClient: ApolloClient = GraphQlProvider.provideApolloClient(apiKey)

    override fun getRepositories(): Single<List<PagedRepository>> {
        val query = SearchMostStarQuery.builder()
            .queryString("stars:>10000 sort:stars")
            .build()

        val apolloCall: ApolloCall<SearchMostStarQuery.Data> = apolloClient.query(query)

        // TODO do proper response error handling!
        val observable: Observable<Response<SearchMostStarQuery.Data>> = Rx2Apollo.from(apolloCall)
        return Single.fromObservable(observable)
            .subscribeOn(subscribeOnScheduler.io)
            .map { response ->
                val list: MutableList<PagedRepository> = mutableListOf()

//                    val repoCount: Int = data?.search()?.repositoryCount() ?: 0
                response.data()?.search()?.edges()?.forEach {
                    val cursor = it.cursor()
                    val node: SearchMostStarQuery.Node? = it.node()
                    if (node is SearchMostStarQuery.AsRepository) {
                        val name = node.name()
                        val id = node.id()
                        val primaryLanguage = node.primaryLanguage()?.name()
                        val starCount = node.stargazers().totalCount()
                        val owner = node.owner().login()

                        val repository = Repository(
                            id,
                            name,
                            owner,
                            null,
                            primaryLanguage,
                            starCount,
                            null
                        )

                        list.add(PagedRepository(repository, cursor))
                    }
                }

                return@map list
            }
    }

    override fun getRepository(owner: String, repoName: String): Single<Repository> {
        val query = GetRepositoryDetailQuery.builder()
            .owner(owner)
            .name(repoName)
            .build()

        val apolloCall: ApolloCall<GetRepositoryDetailQuery.Data> = apolloClient.query(query)

        val observable: Observable<Response<GetRepositoryDetailQuery.Data>> = Rx2Apollo.from(apolloCall)
        return Single.fromObservable(observable)
            .subscribeOn(subscribeOnScheduler.io)
            .map { response ->

                val repository: GetRepositoryDetailQuery.Repository? = response.data()?.repository()

                return@map if (repository != null) {
                    Repository(
                        repository.id(),
                        repository.name(),
                        owner,
                        repository.description(),
                        repository.primaryLanguage()?.name(),
                        repository.stargazers().totalCount(),
                        repository.owner().avatarUrl().toString()
                    )
                } else {
                    Repository.unknownRepository()
                }

            }
    }
}