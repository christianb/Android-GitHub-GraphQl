package de.bunk.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import de.bunk.common.ObserveOnScheduler
import de.bunk.domain.Repository
import de.bunk.domain.repository.detail.RepositoryDetailInteractor
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy

private val TAG = RepositoryDetailViewModel::class.java.simpleName

class RepositoryDetailViewModel(
    private val repositoryDetailInteractor: RepositoryDetailInteractor,
    private val observeOnScheduler: ObserveOnScheduler
) : ViewModel() {

    private val liveData = MutableLiveData<Repository>()
    fun liveData(): LiveData<Repository> = liveData

    private var disposable: Disposable? = null

    fun loadRepositoryDetails(owner: String, repoName: String) {
        disposable = repositoryDetailInteractor.getRepositoryDetail(owner, repoName)
            .observeOn(observeOnScheduler.mainThreadScheduler)
            .subscribeBy(
                onSuccess = { liveData.value = it },
                onError = { Log.d(TAG, "could not fetch data") }
            )
    }

    override fun onCleared() {
        super.onCleared()

        disposable?.dispose()
    }
}