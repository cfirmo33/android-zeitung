package com.segunfamisa.zeitung.util

import kotlinx.coroutines.CoroutineDispatcher

interface CoroutineDispatcherProvider {

    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
    fun main(): CoroutineDispatcher
}