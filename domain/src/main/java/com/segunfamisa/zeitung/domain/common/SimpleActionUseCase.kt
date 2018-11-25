package com.segunfamisa.zeitung.domain.common

/**
 * Type of use case that only executes a simple action and returns no result
 */
abstract class SimpleActionUseCase<in Param> {

    abstract suspend operator fun invoke(param: Param)
}
