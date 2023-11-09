package id.anantyan.foodapps.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.common.UIState
import id.anantyan.foodapps.data.remote.model.ResultsItem
import id.anantyan.foodapps.domain.repository.FoodsUseCase
import id.anantyan.foodapps.domain.repository.PreferencesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val foodsUseCase: FoodsUseCase
) : ViewModel() {
    private var _results: MutableStateFlow<UIState<List<ResultsItem>>> = MutableStateFlow(UIState.Loading())

    val resultsCategories: Flow<List<HomeCategoriesModel>> = flow {
        emit(listOf(
            HomeCategoriesModel(null, "Default"),
            HomeCategoriesModel("main course", "Main Course"),
            HomeCategoriesModel("side dish", "Side Dish"),
            HomeCategoriesModel("dessert", "Dessert"),
            HomeCategoriesModel("appetizer", "Appetizer"),
            HomeCategoriesModel("salad", "Salad"),
            HomeCategoriesModel("bread", "Bread"),
            HomeCategoriesModel("breakfast", "Breakfast"),
            HomeCategoriesModel("soup", "Soup"),
            HomeCategoriesModel("beverage", "Beverage"),
            HomeCategoriesModel("sauce", "Sauce"),
            HomeCategoriesModel("marinade", "Marinade"),
            HomeCategoriesModel("fingerfood", "Fingerfood"),
            HomeCategoriesModel("snack", "Snack"),
            HomeCategoriesModel("drink", "Drink")
        ))
    }
    val results: StateFlow<UIState<List<ResultsItem>>> = _results

    fun results(type: String? = null) {
        viewModelScope.launch {
            foodsUseCase.executeResults(type).collect {
                _results.value = it
            }
        }
    }
}
