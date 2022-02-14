package fr.isen.rouveure.androiderestaurant

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import fr.isen.rouveure.androiderestaurant.databinding.ActivityBasketBinding
import org.json.JSONObject


class BasketActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadList()

        binding.orderButton.setOnClickListener {
            val intent = Intent ( this, UserActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){
            //send order
            Toast.makeText(this, "Panier envoyé", Toast.LENGTH_LONG).show()
            makeRequest()
        }
    }

    private fun loadList() {
        val basket = Basket.getBasket(this)
        val items = basket.items
        binding.recyclerviewBasket.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewBasket.adapter = BasketAdapter(items) {
            basket.removeItem(it)
            basket.save(this)
            loadList()
        }
    }

    private fun makeRequest() {
        val path = "http://test.api.catering.bluecodegames.com/user/order"
        val queue = Volley.newRequestQueue(this)
        val jsonObject = JSONObject()

        val basket = Basket.getBasket(this)
        val sharePreferences = getSharedPreferences(UserActivity.USER_PREFERENCES_NAME, Context.MODE_PRIVATE)

        jsonObject.put("msg",basket.toJson())
        jsonObject.put("id_user",sharePreferences.getInt(UserActivity.ID_USER, -1))
        jsonObject.put("id_shop", 1)

        val request = JsonObjectRequest (
            Request.Method.POST, path, jsonObject, {
                Log.d("request", it.toString(2))
                basket.clear()
                basket.save(this)
                finish()
            }, {
                Log.d("request", it.message ?: "une erreur est survenue")
            }
                )
            queue.add(request)
    }

    companion object {
        const val REQUEST_CODE = 111
    }
}