package id.anantyan.foodapps.data.remote.service

import id.anantyan.foodapps.data.remote.model.RecipeResponse
import id.anantyan.foodapps.data.remote.model.RecipesResponse
import id.anantyan.foodapps.data.remote.network.AppNetwork.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FoodsApi {
    @GET("recipes/search")
    suspend fun results(
        @Query("type") type: String? = null,
        @Query("number") number: Int? = 10,
        @Query("offset") offset: Int? = 0,
        @Query("apiKey") apiKey: String? = API_KEY
    ): Response<RecipesResponse>

    @GET("recipes/{id}/information")
    suspend fun result(
        @Path("id") id: Int? = -1,
        @Query("apiKey") apiKey: String? = API_KEY
    ): Response<RecipeResponse>
}