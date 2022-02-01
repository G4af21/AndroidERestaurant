package fr.isen.rouveure.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.isen.rouveure.androiderestaurant.databinding.ActivityHomeBinding
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // toast on button click event
        // Toast.makeText(this, "Vous avez choisie les entrées.", Toast.LENGTH_LONG).show()
        binding.imgStarter.setOnClickListener {
            changeActivity(getString(R.string.home_starter))
        }

        // toast on button click event
        // Toast.makeText(this, "Vous avez choisie les plats.", Toast.LENGTH_LONG).show()
        binding.imgdish.setOnClickListener {
            changeActivity(getString(R.string.home_dish))
        }

        // toast in button click event
        // Toast.makeText(this, "Vous avez choisie les deserts.", Toast.LENGTH_LONG).show()
        binding.imgdessert.setOnClickListener {
            changeActivity(getString(R.string.home_dessert))
        }

    }

    private fun changeActivity(category: String) {
        val intent = Intent( this, CategoriesActivity::class.java)
        intent.putExtra("category_type", category)
        startActivity(intent)
    }

}