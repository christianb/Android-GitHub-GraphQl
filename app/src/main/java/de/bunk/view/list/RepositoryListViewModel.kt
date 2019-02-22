package de.bunk.view.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.bunk.common.ObserveOnScheduler
import de.bunk.domain.repository.list.RepositoryListInteractor
import de.bunk.domain.Repository
import de.bunk.domain.repository.PagedRepository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

private val TAG = RepositoryListViewModel::class.java.simpleName

class RepositoryListViewModel(
    private val repositoryListInteractor: RepositoryListInteractor,
    private val observeOnScheduler: ObserveOnScheduler
) : ViewModel() {

    private val liveData = MutableLiveData<List<PagedRepository>>()
    fun liveData(): LiveData<List<PagedRepository>> = liveData

    private var disposable: Disposable? = null

    fun fetchRepositories() {
        disposable = repositoryListInteractor.getRepositoryList()
            .observeOn(observeOnScheduler.mainThreadScheduler)
            .subscribeBy(
                onSuccess = {
                    val list = liveData.value ?: emptyList()
                    val mutableList = mutableListOf<PagedRepository>()

                    mutableList.addAll(list)
                    mutableList.addAll(it)

                    liveData.value = mutableList
                },

                onError = {
                    Log.d(TAG, ": ");
                    // todo implement me
                }
            )
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}