package com.segunfamisa.zeitung.domain.newscategories

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.common.UseCase
import javax.inject.Inject

class GetUserNewsPreferencesUseCase @Inject constructor(
    private val preferencesRepository: NewsPreferencesRepository
) : UseCase<Unit, List<String>>() {

    override suspend fun invoke(param: Unit): Either<Error, Result<out List<String>>> {
        return preferencesRepository.getNewsPreferences()
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
