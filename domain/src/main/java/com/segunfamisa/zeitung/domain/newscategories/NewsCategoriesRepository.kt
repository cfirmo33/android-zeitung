package com.segunfamisa.zeitung.domain.newscategories

import arrow.core.Either
import com.segunfamisa.zeitung.domain.common.Error
import com.segunfamisa.zeitung.domain.common.Result

interface NewsCategoriesRepository {
    /**
     * returns a list of categories of preference to a user
     */
    fun getNewsCategories(): Either<Error, Result<List<String>>>
}
