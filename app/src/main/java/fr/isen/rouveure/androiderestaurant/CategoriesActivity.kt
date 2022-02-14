package fr.isen.rouveure.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.rouveure.androiderestaurant.databinding.ActivityCategoriesBinding
import fr.isen.rouveure.androiderestaurant.model.DishModel
import fr.isen.rouveure.androiderestaurant.model.DishResult
import org.json.JSONObject


class CategoriesActivity : BaseActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryType = intent.getStringExtra("category_type") ?: ""
        binding.mainDishTitle.text = categoryType

        loadDishesFromCategory(categoryType)

    }

    private fun loadDishesFromCategory(categorType: String) {
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject, { response ->
                val dishResult = Gson().fromJson(response.toString(), DishResult::class.java)
                displayDishes(dishResult.data.firstOrNull{ category -> category.name_fr == categorType}?.items ?: listOf())

            }, {
                Log.e("DisActivity", "erreur lors de la récupération de la liste des plats")
            })
        Volley.newRequestQueue(this).add(jsonRequest)

    }

    private fun displayDishes(dishes: List<DishModel>) {
        binding.categoriesRecycleView.layoutManager = LinearLayoutManager(this)
        // Setting the Adapter with the recyclerview
        binding.categoriesRecycleView.adapter = DishAdapter(dishes) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", it)
            startActivity(intent)
        }
    }

}