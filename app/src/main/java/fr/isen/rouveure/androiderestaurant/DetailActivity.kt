package fr.isen.rouveure.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.rouveure.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.rouveure.androiderestaurant.model.DishModel


class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("dish") as DishModel
        initDetail(dish)

    }

    private fun initDetail(dish: DishModel) {
        var nbInBucket = 1

        binding.detailTitle.text = dish.name_fr

        binding.dishPhotoPager.adapter = DishPictureAdapter(this, dish.pictures)

        binding.dishIngredient.text = dish.ingredients.joinToString(",") { it.name_fr }

        binding.addDish.setOnClickListener {
            nbInBucket++
            binding.nbDish.text = nbInBucket.toString()
            binding.bucketPrice.text = "" + (dish.prices[0].price.toFloat() * nbInBucket) + "€"
        }

        binding.deletDish.setOnClickListener {
            if (nbInBucket>0)  nbInBucket --
            else nbInBucket = 0
            binding.nbDish.text = nbInBucket.toString()
            binding.bucketPrice.text = "" + (dish.prices[0].price.toFloat() * nbInBucket) + "€"
        }



    }
}