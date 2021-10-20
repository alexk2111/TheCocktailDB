package com.sigmasoftware.akucherenko.thecocktaildb.ui.main

import com.sigmasoftware.akucherenko.thecocktaildb.models.DrinkDetail
import com.sigmasoftware.akucherenko.thecocktaildb.models.Drinks
import com.sigmasoftware.akucherenko.thecocktaildb.models.ToOrder
import retrofit2.http.*

interface CocktailDbInterface {
    @GET ("api/json/v1/{API_KEY}/filter.php")
    suspend fun drinkList(@Path("API_KEY") api_key: Int, @Query("c") c: String): Drinks

    @GET ("api/json/v1/{API_KEY}/lookup.php?")
    suspend fun drinkDetail(@Path("API_KEY") api_key: Int, @Query("i") ID: String): DrinkDetail

    @POST("api/json/v1/{API_KEY}/order")
    suspend fun orderDrink(@Path("API_KEY") api_key: Int, @Body toOrder: ToOrder): String
}