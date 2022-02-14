package fr.isen.rouveure.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import fr.isen.rouveure.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.rouveure.androiderestaurant.model.DishModel


class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailBinding
    var nbInBucket = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("dish") as DishModel
        initDetail(dish)

        /*binding.btnBucketPrice.setOnClickListener{
            val intent = Intent (this, LoginActivity::class.java)
            startActivity(intent)
        }*/

        binding.btnBucketPrice.setOnClickListener {
            val basket = Basket.getBasket(this)
            basket.addItem(dish, nbInBucket)
            basket.save(this)
            Snackbar.make(binding.root, R.string.itemAdded, Snackbar.LENGTH_LONG).show()
            invalidateOptionsMenu()
        }

    }

    private fun initDetail(dish: DishModel) {

        binding.detailTitle.text = dish.name_fr

        binding.dishPhotoPager.adapter = DishPictureAdapter(this, dish.pictures)

        binding.dishIngredient.text = dish.ingredients.joinToString(",") { it.name_fr }

        binding.btnBucketPrice.text = "AJOUTER AU PANIER " + (dish.prices[0].price.toFloat() * nbInBucket) + "€"

        binding.addDish.setOnClickListener {
            nbInBucket++
            binding.nbDish.text = nbInBucket.toString()
            binding.btnBucketPrice.text = "AJOUTER AU PANIER " + (dish.prices[0].price.toFloat() * nbInBucket) + "€"
        }

        binding.deletDish.setOnClickListener {
            if (nbInBucket>1)  nbInBucket --
            else nbInBucket = 1
            binding.nbDish.text = nbInBucket.toString()
            binding.btnBucketPrice.text = "AJOUTER AU PANIER  " + (dish.prices[0].price.toFloat() * nbInBucket) + "€"
        }
    }
}