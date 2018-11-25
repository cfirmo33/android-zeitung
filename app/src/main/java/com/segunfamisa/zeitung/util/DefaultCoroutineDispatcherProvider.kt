package com.segunfamisa.zeitung.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultCoroutineDispatcherProvider @Inject constructor() : CoroutineDispatcherProvider {

    override fun io(): CoroutineDispatcher = Dispatchers.IO

    override fun computation(): CoroutineDispatcher = Dispatchers.Default

    override fun main(): CoroutineDispatcher = Dispatchers.Main
}
