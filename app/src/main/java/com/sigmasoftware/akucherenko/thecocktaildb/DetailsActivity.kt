package com.sigmasoftware.akucherenko.thecocktaildb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.sigmasoftware.akucherenko.thecocktaildb.databinding.ActivityDetailsBinding
import com.sigmasoftware.akucherenko.thecocktaildb.models.*
import com.sigmasoftware.akucherenko.thecocktaildb.ui.main.CocktailDbInterface
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var detailDrink: DrinkDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.getStringExtra("id") ?: "0"

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                detailDrink = getData(id)
            }
            val detailDrinkItem = detailDrink.drinks
            binding.drinkName.text = detailDrinkItem[0].strDrink.toString()
            binding.drinkCategory.text = detailDrinkItem[0].strCategory.toString()
            binding.drinkAlcoholic.text = detailDrinkItem[0].strAlcoholic.toString()
            binding.drinkGlass.text = detailDrinkItem[0].strGlass.toString()
            binding.instruction.text = detailDrinkItem[0].strInstructions.toString()
            Picasso.get().load(detailDrinkItem[0].strDrinkThumb).into(binding.detailPicture)
        }
    }

    private suspend fun getData(id: String): DrinkDetail {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://thecocktaildb.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()

        val service = retrofit.create(CocktailDbInterface::class.java)

        return service.drinkDetail(1, id)
    }

    fun onClickSendOrder(view: android.view.View) {
        var price: Price = Price(100, "USD")
        val ordersItem: OrdersItem = OrdersItem(price, "Margarita", 12)
        val client: Client = Client(id = 12, name = "Bob")
        val orderDate = "25-12-2021:10-30-00"
        val order: ToOrder = ToOrder(client, listOf(ordersItem), orderDate )

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://thecocktaildb.com/")
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .build()
                val service = retrofit.create(CocktailDbInterface::class.java)
                service.orderDrink(1, order)
            }
        }
    }
}