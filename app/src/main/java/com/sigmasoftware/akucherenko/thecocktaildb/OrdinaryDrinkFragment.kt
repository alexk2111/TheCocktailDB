package com.sigmasoftware.akucherenko.thecocktaildb

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.sigmasoftware.akucherenko.thecocktaildb.databinding.FragmentOrdinaryDrinkBinding
import com.sigmasoftware.akucherenko.thecocktaildb.models.Drinks
import com.sigmasoftware.akucherenko.thecocktaildb.ui.main.CocktailDbInterface
import com.sigmasoftware.akucherenko.thecocktaildb.ui.main.DrinkAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OrdinaryDrink.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdinaryDrink : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var ordinaryDrink: Drinks
    private var drinkList = Drinks().drinks

    private lateinit var drinkAdapter: DrinkAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var _binding: FragmentOrdinaryDrinkBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentOrdinaryDrinkBinding.inflate(inflater, container, false)
        val viewFragment = binding.root
        swipeRefreshLayout = binding.refresher
        swipeRefreshLayout.setOnRefreshListener(this)
        val progress = binding.progress
        progress.visibility = View.VISIBLE

        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                ordinaryDrink = getData()
            }
            drinkList.addAll(ordinaryDrink.drinks)
            drinkAdapter = DrinkAdapter(drinkList)
            binding.recyclerView.adapter = drinkAdapter
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            drinkAdapter.notifyDataSetChanged()
            progress.visibility = View.GONE
        }
//        return inflater.inflate(R.layout.fragment_ordinary_drink, container, false)
        return viewFragment

    }

    private suspend fun getData(): Drinks {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://thecocktaildb.com/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .build()
        val service = retrofit.create(CocktailDbInterface::class.java)

        return service.drinkList(1, "Ordinary_Drink")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdinaryDrink.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdinaryDrink().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh() {
        GlobalScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                ordinaryDrink = getData()
            }
            drinkList.addAll(ordinaryDrink.drinks)
            drinkAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
    }
}