package com.testing.demoapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.testing.demoapi.databinding.ItemBeerHolderBinding
import com.testing.wsconnector.models.BeerData

class AdapterBeer(private val items: List<BeerData>, val listener: (BeerData) -> Unit): RecyclerView.Adapter<AdapterBeer.BeerViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return BeerViewHolder(inflater.inflate(R.layout.item_beer_holder, parent, false))
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        holder.bind(items.get(position), listener)
        Picasso.with(holder.itemView.context)
            .load(items.get(position).imageUrl)
            .into(holder.binding.imgBeer)
        holder.binding.imgBeer
    }

    class BeerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val binding: ItemBeerHolderBinding = ItemBeerHolderBinding.bind(itemView)

        fun bind(beerData: BeerData, listener: (BeerData) -> Unit) {
            binding.textDescription.text = beerData.description
            binding.titleBeer.text = beerData.name
            binding.root.setOnClickListener { listener(beerData) }
        }
    }
}