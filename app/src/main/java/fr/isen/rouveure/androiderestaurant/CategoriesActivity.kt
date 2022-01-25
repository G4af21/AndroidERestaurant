package fr.isen.rouveure.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.rouveure.androiderestaurant.databinding.ActivityCategoriesBinding


class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mainDishTitle.text = intent.getStringExtra("category_type")

        // getting the recyclerview by its id
        //val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        binding.categoriesRecycleView.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()

        // This loop will create 20 Views containing
        // the image with the count of view
        /*for (i in 1..20) {
            data.add(ItemsViewModel( "Item " + i))
        } */
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))
        data.add(ItemsViewModel(R.drawable.desert, "Gateau au chocolat"))


        // This will pass the ArrayList to our Adapter
        val adapter = DishAdapter(data)

        // Setting the Adapter with the recyclerview
        binding.categoriesRecycleView.adapter = adapter



    }


}