package id.anantyan.foodapps.domain.repository

import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.RecipesResponse
import id.anantyan.foodapps.data.remote.model.ResultsItem
import kotlinx.coroutines.flow.Flow

interface FoodsRepository {
    fun results(type: String?): Flow<UIState<List<ResultsItem>>>
    fun result(id: Int?): Flow<UIState<RecipeResponse>>
}