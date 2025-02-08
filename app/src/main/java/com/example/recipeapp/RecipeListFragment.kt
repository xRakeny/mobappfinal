package com.example.recipeapp


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class RecipeListFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var recipeAdapter: RecipeAdapter
    private val recipeList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        database =
            FirebaseDatabase.getInstance("https://btufinal-5dbf3-default-rtdb.firebaseio.com")
                .getReference("recipes") 

        recipeAdapter = RecipeAdapter(recipeList)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = recipeAdapter

        fetchRecipes()

//        val spaghettiCarbonara = Recipe(
//            name = "Spaghetti Carbonara",
//            ingredients = "Spaghetti, Eggs, Parmesan cheese, Bacon, Black pepper",
//            instructions = "Cook spaghetti. Fry bacon and mix with beaten eggs and Parmesan. Combine with spaghetti and toss with pepper.",
//            imageUrl = "https://www.allrecipes.com/thmb/Vg2cRidr2zcYhWGvPD8M18xM_WY=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/11973-spaghetti-carbonara-ii-DDMFS-4x3-6edea51e421e4457ac0c3269f3be5157.jpg"
//        )
//        val chickenCurry = Recipe(
//            name = "Chicken Curry",
//            ingredients = "Chicken, Onion, Garlic, Ginger, Tomatoes, Coconut milk, Spices",
//            instructions = "Fry onions, garlic, and ginger. Add chicken and spices, cook until browned. Add tomatoes and coconut milk, simmer until cooked.",
//            imageUrl = "https://www.allrecipes.com/thmb/FL-xnyAllLyHcKdkjUZkotVlHR8=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/46822-indian-chicken-curry-ii-DDMFS-4x3-39160aaa95674ee395b9d4609e3b0988.jpg"
//        )
//        val caesarSalad = Recipe(
//            name = "Caesar Salad",
//            ingredients = "Lettuce, Croutons, Caesar dressing, Parmesan cheese, Chicken (optional)",
//            instructions = "Toss lettuce with dressing, croutons, and cheese. Add chicken if desired. Serve chilled.",
//            imageUrl = "https://cdn.loveandlemons.com/wp-content/uploads/2024/12/caesar-salad.jpg"
//        )
//        val beefStew = Recipe(
//            name = "Beef Stew",
//            ingredients = "Beef, Carrots, Potatoes, Onion, Garlic, Beef broth, Herbs",
//            instructions = "Brown beef and vegetables. Add broth and herbs, simmer until tender.",
//            imageUrl = "https://static01.nyt.com/images/2024/10/28/multimedia/beef-stew-mlfk/beef-stew-mlfk-googleFourByThree.jpg"
//        )
//        val tacos = Recipe(
//            name = "Tacos",
//            ingredients = "Taco shells, Ground beef, Lettuce, Cheese, Salsa",
//            instructions = "Cook beef and season with taco seasoning. Fill taco shells with beef and toppings.",
//            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/7/73/001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg/800px-001_Tacos_de_carnitas%2C_carne_asada_y_al_pastor.jpg"
//        )
//
//
//
//        database.push().setValue(spaghettiCarbonara)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(chickenCurry)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 2 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(caesarSalad)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(beefStew)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 2 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(tacos)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 2 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//
//            }
    }
    private fun fetchRecipes() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                recipeList.clear()

                for (recipeSnapshot in snapshot.children) {
                    val recipe = recipeSnapshot.getValue(Recipe::class.java)


                    recipe?.let {
                        if (!recipeList.contains(it)) {
                            recipeList.add(it) //
                        }
                    }
                }

                recipeAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read data", error.toException())
            }
        })
    }

}
