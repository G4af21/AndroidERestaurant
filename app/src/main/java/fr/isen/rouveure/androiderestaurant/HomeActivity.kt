package fr.isen.rouveure.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var button2 = findViewById(R.id.button2) as Button

        button2.setOnClickListener {
            // toast on button click event
            Toast.makeText(this, "Vous avez choisie les entrées.", Toast.LENGTH_LONG).show()
        }
        var button3 = findViewById(R.id.button3) as Button

        button3.setOnClickListener {
            // toast on button click event
            Toast.makeText(this, "Vous avez choisie les plats.", Toast.LENGTH_LONG).show()
        }

        var button4 = findViewById(R.id.button4) as Button

        button4.setOnClickListener {
            // toast in button click event
            Toast.makeText(this, "Vous avez choisie les deserts.", Toast.LENGTH_LONG).show()
        }
    }
}