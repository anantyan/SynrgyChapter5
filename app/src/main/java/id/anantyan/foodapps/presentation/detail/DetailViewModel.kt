package id.anantyan.foodapps.presentation.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import kotlinx.coroutines.flow.Flow

class DetailViewModel(
    private val foodsUseCase: FoodsUseCase
) : ViewModel() {
    fun result(id: Int?): Flow<UIState<RecipeResponse>> = foodsUseCase.executeResult(id)
}