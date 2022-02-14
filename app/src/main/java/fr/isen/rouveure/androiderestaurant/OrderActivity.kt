package fr.isen.rouveure.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.rouveure.androiderestaurant.databinding.ActivityOrderBinding

class OrderActivity : AppCompatActivity() {

    //private lateinit var binding: ActivityOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        /*binding.backHomeLogo.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity((intent))
        }*/
    }
}