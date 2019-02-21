package de.bunk.data

import SearchMostStarQuery
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.rx2.Rx2Apollo
import de.bunk.common.SubscribeOnScheduler
import de.bunk.domain.GitHubDataSource
import de.bunk.domain.Repository
import de.bunk.view.GraphQlProvider
import io.reactivex.Observable
import io.reactivex.Single

class GitHubDataSourceImpl(
    private val subscribeOnScheduler: SubscribeOnScheduler
) : GitHubDataSource {

    private val apolloClient: ApolloClient = GraphQlProvider.provideApolloClient()

    override fun getRepositories(): Single<List<Repository>> {
        val query = SearchMostStarQuery.builder()
            .queryString("stars:>10000 sort:stars")
            .build()

        val apolloCall: ApolloCall<SearchMostStarQuery.Data> = apolloClient.query(query)

        // TODO do proper response error handling!
        val observable: Observable<Response<SearchMostStarQuery.Data>> = Rx2Apollo.from(apolloCall)
        return Single.fromObservable(observable)
            .subscribeOn(subscribeOnScheduler.io)
            .map { response ->
                val list: MutableList<Repository> = mutableListOf()

//                    val repoCount: Int = data?.search()?.repositoryCount() ?: 0
                response.data()?.search()?.edges()?.forEach {
                    val cursor = it.cursor()
                    val node: SearchMostStarQuery.Node? = it.node()
                    if (node is SearchMostStarQuery.AsRepository) {
                        val name = node.name()
                        val description = node.description()
                        val id = node.id()
                        val primaryLanguage = node.primaryLanguage()?.name()
                        val starCount = node.stargazers().totalCount()

                        val repository = Repository(
                            id,
                            name,
                            description,
                            primaryLanguage,
                            starCount,
                            cursor
                        )

                        list.add(repository)
                    }
                }

                return@map list
            }
    }
}