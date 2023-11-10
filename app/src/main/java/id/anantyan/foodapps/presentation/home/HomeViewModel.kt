package id.anantyan.foodapps.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.anantyan.foodapps.R
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
            HomeCategoriesModel(null, R.string.txt_value_default),
            HomeCategoriesModel("main course", R.string.txt_value_main_course),
            HomeCategoriesModel("side dish", R.string.txt_value_side_dish),
            HomeCategoriesModel("dessert", R.string.txt_value_dessert),
            HomeCategoriesModel("appetizer", R.string.txt_value_appetizer),
            HomeCategoriesModel("salad", R.string.salad),
            HomeCategoriesModel("bread", R.string.bread),
            HomeCategoriesModel("breakfast", R.string.breakfast),
            HomeCategoriesModel("soup", R.string.soup),
            HomeCategoriesModel("beverage", R.string.beverage),
            HomeCategoriesModel("sauce", R.string.sauce),
            HomeCategoriesModel("marinade", R.string.marinade),
            HomeCategoriesModel("fingerfood", R.string.fingerfood),
            HomeCategoriesModel("snack", R.string.snack),
            HomeCategoriesModel("drink", R.string.drink)
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
