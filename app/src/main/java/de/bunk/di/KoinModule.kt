package de.bunk.di

import de.bunk.common.ObserveOnScheduler
import de.bunk.common.SubscribeOnScheduler
import de.bunk.common.SubscribeOnSchedulerImpl
import de.bunk.data.ApiKey
import de.bunk.data.GitHubDataSourceImpl
import de.bunk.domain.GitHubDataSource
import de.bunk.domain.repository.detail.RepositoryDetailInteractor
import de.bunk.domain.repository.list.RepositoryListInteractor
import de.bunk.util.ObserveOnSchedulerImpl
import de.bunk.view.BuildConfig
import de.bunk.view.detail.RepositoryDetailViewModel
import de.bunk.view.list.RepositoryListViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel

val module = org.koin.dsl.module.module {

  viewModel { RepositoryListViewModel(get(), get()) }
  viewModel { RepositoryDetailViewModel(get(), get()) }

  single<GitHubDataSource> { GitHubDataSourceImpl(get(), get()) }
  factory<RepositoryListInteractor> { RepositoryListInteractor(get()) }
  factory<RepositoryDetailInteractor> { RepositoryDetailInteractor(get()) }

  factory<ObserveOnScheduler> { ObserveOnSchedulerImpl() }
  factory<SubscribeOnScheduler> { SubscribeOnSchedulerImpl() }

  single<ApiKey> {
    object : ApiKey {
      override val value = BuildConfig.GITHUB_API_KEY
    }
  }
}