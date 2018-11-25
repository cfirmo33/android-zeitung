package com.segunfamisa.zeitung.domain.newscategories

import arrow.core.Either
import com.segunfamisa.zeitung.core.entities.Category
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result
import com.segunfamisa.zeitung.domain.common.UseCase
import javax.inject.Inject

/**
 * returns a list of all currently supported [Category]
 */
class GetAllCategoriesUseCase @Inject constructor() : UseCase<Unit, List<Category>>() {

    override suspend fun invoke(param: Unit): Either<Error, Result<out List<Category>>> {
        return Either.right(
            Result(
                data = listOf(
                    Category.Business(),
                    Category.Entertainment(),
                    Category.General(),
                    Category.Health(),
                    Category.Science(),
                    Category.Sports(),
                    Category.Technology()
                )
            )
        )
    }

    override fun cancel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
