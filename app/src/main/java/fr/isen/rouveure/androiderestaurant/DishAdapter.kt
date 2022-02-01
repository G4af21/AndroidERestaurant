package fr.isen.rouveure.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.rouveure.androiderestaurant.model.DishModel


class DishAdapter(private val mList: List<DishModel>, val onDishClicked: (DishModel) -> Unit) : RecyclerView.Adapter<DishAdapter.ViewHolder>() {

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val dishName: TextView = itemView.findViewById(R.id.cellDishName)
        val dishPicture:  ImageView = itemView.findViewById(R.id.cellImageDish)
        val dishPrice: TextView = itemView.findViewById(R.id.cellDishPrice)
    }

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val dishModel = mList[position]

        holder.dishName.text = dishModel.name_fr

        Picasso.get()
            .load(mList[position].getFirstPicture())
            .error(R.drawable.logo)
            .placeholder(R.drawable.logo)
            .into(holder.dishPicture)
        
        holder.dishPrice.text = dishModel.getFormattedPrice()

        holder.itemView.setOnClickListener {
            onDishClicked(dishModel)
        }

    }

    // return the number of the items in the list)
    override fun getItemCount(): Int {
        return mList.size
    }

}

