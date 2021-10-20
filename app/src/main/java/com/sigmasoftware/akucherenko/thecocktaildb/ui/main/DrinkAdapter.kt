package com.sigmasoftware.akucherenko.thecocktaildb.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sigmasoftware.akucherenko.thecocktaildb.DetailsActivity
import com.sigmasoftware.akucherenko.thecocktaildb.models.DrinksDetailItem
import com.sigmasoftware.akucherenko.thecocktaildb.databinding.ItemDrinkBinding
import com.squareup.picasso.Picasso

class DrinkAdapter(private val drinkList: List<DrinksDetailItem>) :
    RecyclerView.Adapter<DrinkAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDrinkBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(drinkList[position])
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }

    class ViewHolder(private val binding: ItemDrinkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DrinksDetailItem) = with(binding) {
            Picasso.get().load(item.strDrinkThumb).into(picture)
            strDrink.text = item.strDrink
            idDrink.text = item.idDrink
            binding.root.setOnClickListener {
                var intent = Intent(it.context, DetailsActivity::class.java)
                intent.putExtra("id", item.idDrink)
                it.context.startActivity(intent)
            }
        }
    }
}