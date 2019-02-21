package de.bunk.view.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.bunk.common.ObserveOnScheduler
import de.bunk.domain.GitHubInteractor
import de.bunk.domain.Repository
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

private val TAG = RepositoryListViewModel::class.java.simpleName

class RepositoryListViewModel(
    private val gitHubInteractor: GitHubInteractor,
    private val observeOnScheduler: ObserveOnScheduler
) : ViewModel() {

    private val liveData = MutableLiveData<List<Repository>>()
    fun liveData(): LiveData<List<Repository>> = liveData

    private var disposable: Disposable? = null

    fun fetchRepositories() {
        disposable = gitHubInteractor.getRepositoryList()
            .observeOn(observeOnScheduler.mainThreadScheduler)
            .subscribeBy(
                onSuccess = {
                    val list = liveData.value ?: emptyList()
                    val mutableList = mutableListOf<Repository>()

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