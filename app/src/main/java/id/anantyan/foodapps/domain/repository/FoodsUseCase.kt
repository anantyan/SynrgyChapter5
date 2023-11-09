package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.RecipesResponse
import id.anantyan.foodapps.data.remote.model.ResultsItem
import kotlinx.coroutines.flow.Flow

class FoodsUseCase(private val foodsRepository: FoodsRepository) {
    fun executeResults(type: String?): Flow<UIState<List<ResultsItem>>> = foodsRepository.results(type)
    fun executeResult(id: Int?): Flow<UIState<RecipeResponse>> = foodsRepository.result(id)
}