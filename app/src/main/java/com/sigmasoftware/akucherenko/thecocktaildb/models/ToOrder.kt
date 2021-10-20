package com.sigmasoftware.akucherenko.thecocktaildb.models

import com.google.gson.annotations.SerializedName

data class ToOrder(

	@field:SerializedName("client")
	val client: Client? = null,

	@field:SerializedName("orders")
	val orders: List<OrdersItem?>? = null,

	@field:SerializedName("orderDate")
	val orderDate: String? = null
)

data class OrdersItem(

	@field:SerializedName("price")
	val price: Price? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Client(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class Price(

	@field:SerializedName("amount")
	val amount: Int? = null,

	@field:SerializedName("currency")
	val currency: String? = null
)
