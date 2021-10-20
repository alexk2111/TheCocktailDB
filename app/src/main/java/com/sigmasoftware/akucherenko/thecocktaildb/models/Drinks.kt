package com.sigmasoftware.akucherenko.thecocktaildb.models

import com.google.gson.annotations.SerializedName

data class Drinks(

	@field:SerializedName("drinks")
	val drinks: MutableList<DrinksDetailItem> = mutableListOf()
)

data class DrinksItem(

	@field:SerializedName("strDrink")
	val strDrink: String? = null,

	@field:SerializedName("strDrinkThumb")
	val strDrinkThumb: String? = null,

	@field:SerializedName("idDrink")
	val idDrink: String? = null
)
