package com.segunfamisa.zeitung

import com.segunfamisa.zeitung.util.CoroutineDispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class TestCoroutineDispatcherProvider : CoroutineDispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun computation(): CoroutineDispatcher = Dispatchers.Unconfined

    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
}
