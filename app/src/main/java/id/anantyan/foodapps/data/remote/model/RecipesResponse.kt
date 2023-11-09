package id.anantyan.foodapps.data.remote.model

import com.google.gson.annotations.SerializedName
import id.anantyan.foodapps.data.remote.network.AppNetwork.BASE_IMAGE

data class RecipesResponse(
	@field:SerializedName("results")
	val results: List<ResultsItem>? = null
)

data class ResultsItem(

	@field:SerializedName("readyInMinutes")
	val readyInMinutes: Int? = null,

	@field:SerializedName("image")
	private val _image: String? = null,

	@field:SerializedName("servings")
	val servings: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
) {
	val image get() = "$BASE_IMAGE$_image"
}
