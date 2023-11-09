package id.anantyan.foodapps.data.remote.repository

import com.google.gson.Gson
import id.anantyan.foodapps.R
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.RecipesResponse
import id.anantyan.foodapps.data.remote.model.ResultsItem
import id.anantyan.foodapps.data.remote.service.FoodsApi
import id.anantyan.foodapps.domain.repository.FoodsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FoodsRepositoryImpl(
    private val foodsApi: FoodsApi
) : FoodsRepository {
    override fun results(type: String?): Flow<UIState<List<ResultsItem>>> {
        return flow {
            emit(UIState.Loading())
            val response = foodsApi.results(type)
            if (response.isSuccessful) {
                emit(UIState.Success(response.body()?.results ?: emptyList()))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }

    override fun result(id: Int?): Flow<UIState<RecipeResponse>> {
        return flow {
            emit(UIState.Loading())
            val response = foodsApi.result(id)
            if (response.isSuccessful) {
                emit(UIState.Success(response.body() ?: RecipeResponse()))
            } else {
                emit(UIState.Error(null, R.string.txt_invalid_get_results))
            }
        }
    }
}