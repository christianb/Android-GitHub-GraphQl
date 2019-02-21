package de.bunk.di

import de.bunk.common.ObserveOnScheduler
import de.bunk.common.SubscribeOnScheduler
import de.bunk.common.SubscribeOnSchedulerImpl
import de.bunk.data.GitHubDataSourceImpl
import de.bunk.domain.GitHubDataSource
import de.bunk.domain.GitHubInteractor
//import de.bunk.domain.GitHubDataSource
//import de.bunk.domain.GitHubInteractor
import de.bunk.util.ObserveOnSchedulerImpl
import de.bunk.view.list.RepositoryListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel

private const val BASE_URL = "https://api.github.com/"

val module = org.koin.dsl.module.module {

  //    single<OkHttpClient> { createRestOkHttpClient() }
//    single<Retrofit> { RetrofitProvider.provideRetrofit(BASE_URL, get()) }
//    single<GitHubApi> { get<Retrofit>().create(GitHubApi::class.java) }
//    factory<GitHubRestDataSource> { GitHubRestDataSourceImpl(get()) }

//    single<ApolloClient> { GraphQlProvider.provideApolloClient("https://api.github.com/graphql") }
//
//    factory<ObservableProvider> { ObservableProvider() }
//    factory<GitHubRepository> { GitHubRepositoryImpl(get()) }
//
  viewModel { RepositoryListViewModel(get(), get()) }

  single<GitHubDataSource> { GitHubDataSourceImpl(get()) }
  factory<GitHubInteractor> { GitHubInteractor(get()) }

  factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
  factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }

}