package com.segunfamisa.zeitung.domain.newscategories

import com.segunfamisa.zeitung.domain.common.SimpleActionUseCase
import javax.inject.Inject

class AddPreferredNewsCategoryUseCase @Inject constructor(
    private val newsPreferencesRepository: NewsPreferencesRepository
) : SimpleActionUseCase<String>() {

    override suspend fun invoke(param: String) {
        return newsPreferencesRepository.addNewsCategory(category = param)
    }
}
