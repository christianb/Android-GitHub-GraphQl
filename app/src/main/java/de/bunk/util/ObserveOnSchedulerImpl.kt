package de.bunk.util

import de.bunk.common.ObserveOnScheduler
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class ObserveOnSchedulerImpl : ObserveOnScheduler {
    override val mainThreadScheduler: Scheduler = AndroidSchedulers.mainThread()
}