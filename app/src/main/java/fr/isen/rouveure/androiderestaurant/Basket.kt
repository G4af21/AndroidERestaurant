package fr.isen.rouveure.androiderestaurant

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import fr.isen.rouveure.androiderestaurant.model.DishModel
import java.io.File
import java.io.Serializable

class Basket(val items: MutableList<BasketItem>): Serializable {

    var itemsCount: Int = 0
        get() {
            var count = 0
            items.forEach {
                count += it.quantity
            }
        return count
        }

    fun addItem(item: DishModel, quantity: Int) {
        val existingItem = items.firstOrNull { it.dish.name_fr == item.name_fr}
        //val existingItem = items.filter { it.dish.name_fr }.firstOrNull()
        existingItem?.let {
            existingItem.quantity += quantity
        } ?: run {
            val basketItem = BasketItem(item, quantity)
            items.add(basketItem)
        }
    }

    fun removeItem(basketItem: BasketItem) {
        items.remove(basketItem)
    }

    fun clear() {
        items.removeAll { true }
    }

    fun save(context: Context) {
        val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
        jsonFile.writeText(toJson())
        updateCounter(context)
    }

    fun toJson(): String {
       return GsonBuilder().create().toJson(this)
    }

    private fun updateCounter(context: Context) {
        val sharedPreferences = context.getSharedPreferences(USER_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(ITEMS_COUNT, itemsCount)
        editor.apply()
    }

    companion object {
        fun getBasket(context: Context): Basket {
            val count = Basket(mutableListOf()).itemsCount
            val jsonFile = File(context.cacheDir.absolutePath + BASKET_FILE)
            if(jsonFile.exists()){
                val json = jsonFile.readText()
                return GsonBuilder().create().fromJson(json, Basket::class.java)
            }else {
               return Basket(mutableListOf())
            }
        }
        const val BASKET_FILE = "basket.json"
        const val ITEMS_COUNT = "ITEMS_COUNT"
        const val USER_PREFERENCES_NAME = "USER_PREFERENCES_NAME"
    }


}

class BasketItem(val dish: DishModel, var quantity: Int): Serializable