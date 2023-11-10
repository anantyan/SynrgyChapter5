package id.anantyan.foodapps.data.remote.model

import com.google.gson.annotations.SerializedName
import id.anantyan.foodapps.data.remote.network.AppNetwork
import id.anantyan.foodapps.data.remote.network.AppNetwork.BASE_IMAGE

data class RecipeResponse(
	@field:SerializedName("analyzedInstructions")
	val analyzedInstructions: List<AnalyzedInstructionsItem>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("preparationMinutes")
	val preparationMinutes: Int? = null,

	@field:SerializedName("sourceName")
	val sourceName: String? = null,

	@field:SerializedName("servings")
	val servings: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("image")
	private val _image: String? = null,

	@field:SerializedName("extendedIngredients")
	val extendedIngredients: List<ExtendedIngredientsItem>? = null
) {
	val image get() = _image
}

data class AnalyzedInstructionsItem(
	@field:SerializedName("steps")
	val steps: List<StepsItem>? = null
)

data class ExtendedIngredientsItem(
	@field:SerializedName("original")
	val original: String? = null
)

data class StepsItem(
	@field:SerializedName("step")
	val step: String? = null
)
