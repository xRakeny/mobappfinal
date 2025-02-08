package com.example.recipeapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class RecipeAdapter(private val recipeList: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {

    class RecipeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.recipeTitle)
        val ingredients: TextView = view.findViewById(R.id.recipeIngredients)
        val instructions: TextView = view.findViewById(R.id.recipeInstructions)
        val recipeImage: ImageView = view.findViewById(R.id.recipeImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recipe, parent, false)
        return RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val recipe = recipeList[position]

        // Set text values
        holder.title.text = recipe.name
        holder.ingredients.text = "Ingredients: ${recipe.ingredients}"
        holder.instructions.text = "Instructions: ${recipe.instructions}"

        // Load image using
        Picasso.get()
            .load(recipe.imageUrl) // Load image URL from Recipe object
            .placeholder(android.R.drawable.ic_menu_gallery) // Default placeholder
            .error(android.R.drawable.ic_menu_report_image) // Image on load failure
            .into(holder.recipeImage) // Set image in ImageView
    }

    override fun getItemCount() = recipeList.size
}

// DrinkAdapter for Drinks Tab
class DrinkAdapter(private val drinkList: MutableList<Recipe>) : RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false) // Uses same layout
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val drink = drinkList[position]
        holder.bind(drink)
    }

    override fun getItemCount(): Int = drinkList.size

    inner class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.recipeTitle)
        private val ingredients: TextView = itemView.findViewById(R.id.recipeIngredients)
        private val instructions: TextView = itemView.findViewById(R.id.recipeInstructions)
        private val recipeImage: ImageView = itemView.findViewById(R.id.recipeImage)

        fun bind(drink: Recipe) {
            title.text = drink.name
            ingredients.text = "Ingredients: ${drink.ingredients}"
            instructions.text = "Instructions: ${drink.instructions}"

            // Load image using Picasso
            Picasso.get()
                .load(drink.imageUrl)
                .placeholder(android.R.drawable.ic_menu_gallery)
                .error(android.R.drawable.ic_menu_report_image)
                .into(recipeImage)
        }
    }
}
