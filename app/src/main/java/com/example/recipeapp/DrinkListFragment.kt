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

class DrinkListFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private lateinit var drinkAdapter: DrinkAdapter
    private val drinkList = mutableListOf<Recipe>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_drink_list, container, false) 
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        database =
            FirebaseDatabase.getInstance("https://btufinal-5dbf3-default-rtdb.firebaseio.com")
                .getReference("drinks")

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        drinkAdapter = DrinkAdapter(drinkList)
        recyclerView.adapter = drinkAdapter

        fetchDrinks()

//        val margarita = Recipe(
//            name = "Margarita",
//            ingredients = "Tequila, Lime juice, Triple sec, Salt",
//            instructions = "Rim the glass with salt. Shake tequila, lime juice, and triple sec with ice. Pour into the glass and garnish with lime.",
//            imageUrl = "https://mixthatdrink.com/wp-content/uploads/2023/03/classic-margarita-cocktail-540x720.jpg"
//        )
//
//
//        val mojito = Recipe(
//            name = "Mojito",
//            ingredients = "White rum, Mint leaves, Sugar, Lime, Soda water",
//            instructions = "Muddle mint leaves and sugar in a glass. Add lime juice and rum, then fill with soda water. Stir and garnish with mint.",
//            imageUrl = "https://cookieandkate.com/images/2020/08/best-mojito-recipe-2.jpg"
//        )
//        val oldFashioned = Recipe(
//            name = "Old Fashioned",
//            ingredients = "Whiskey, Sugar, Angostura bitters, Orange peel",
//            instructions = "Muddle sugar and bitters in a glass. Add whiskey and ice. Stir and garnish with orange peel.",
//            imageUrl = "https://cdn.apartmenttherapy.info/image/upload/f_jpg,q_auto:eco,c_fill,g_auto,w_1500,ar_1:1/k%2FPhoto%2FRecipes%2F2024-10-old-fashioned%2Fold-fashioned-0898-vertical"
//        )
//        val pinaColada = Recipe(
//            name = "Pina Colada",
//            ingredients = "Rum, Coconut cream, Pineapple juice",
//            instructions = "Blend all ingredients with ice until smooth. Serve in a chilled glass and garnish with a pineapple slice.",
//            imageUrl = "https://www.simplyrecipes.com/thmb/zY68crK2iBgGYNRQ9FqTakwRn_Q=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Simply-Recipes-Pina-Colada-LEAD-4-d0d465dd3c26411ea3623c7ed2d82709.jpg"
//        )
//
//        val cosmopolitan = Recipe(
//            name = "Cosmopolitan",
//            ingredients = "Vodka, Triple sec, Cranberry juice, Lime juice",
//            instructions = "Shake all ingredients with ice and strain into a martini glass. Garnish with a lime wedge.",
//            imageUrl = "https://www.giallozafferano.com/images/274-27454/Cosmopolitan_650x433_wm.jpg"
//        )
//
//        database.push().setValue(margarita)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//
//        database.push().setValue(mojito)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(oldFashioned)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//        database.push().setValue(pinaColada)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }
//
//        database.push().setValue(cosmopolitan)
//            .addOnSuccessListener {
//                Log.d("Firebase", "Recipe 1 added successfully")
//            }
//            .addOnFailureListener {
//                Log.e("Firebase", "Failed to add Recipe 1", it)
//            }


    }

    private fun fetchDrinks() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                drinkList.clear()
                for (drinkSnapshot in snapshot.children) {
                    val drink = drinkSnapshot.getValue(Recipe::class.java)
                    drink?.let { drinkList.add(it) }
                }
                drinkAdapter.notifyDataSetChanged() 
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read data", error.toException())
            }
        })
    }

}
