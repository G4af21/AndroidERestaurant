package fr.isen.rouveure.androiderestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.rouveure.androiderestaurant.databinding.CardViewBasketBinding

class BasketAdapter(private val items: List<BasketItem>, val deleteCLickList: (BasketItem) -> Unit): RecyclerView.Adapter<BasketAdapter.BasketViewHolder>() {

   lateinit var context: Context

    class BasketViewHolder(binding: CardViewBasketBinding): RecyclerView.ViewHolder(binding.root) {
        val dishBasketName: TextView = binding.cellBasketName
        val priceBasket: TextView = binding.cellBasketPrice
        val quantity: TextView = binding.quantity
        val delete: ImageView = binding.deleteBasket
        val imDishBasket: ImageView = binding.cellImageBasket
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BasketViewHolder {
        context = parent.context
        return BasketViewHolder(CardViewBasketBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: BasketViewHolder, position: Int) {
        val basketItem = items[position]
        holder.dishBasketName.text = basketItem.dish.name_fr
        holder.priceBasket.text = "${basketItem.dish.prices.first().price} €"
        holder.quantity.text = "${context.getString(R.string.quantity)} ${basketItem.quantity.toString()}"
        holder.delete.setOnClickListener {
            deleteCLickList.invoke(basketItem)
        }

        Picasso.get()
            .load(basketItem.dish.getFirstPicture())
            .error(R.drawable.noimages)
            .placeholder(R.drawable.noimages)
            .into(holder.imDishBasket)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

}