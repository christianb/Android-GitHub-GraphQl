package de.bunk.common

import io.reactivex.Scheduler

interface ObserveOnScheduler {
    val mainThreadScheduler: Scheduler
}