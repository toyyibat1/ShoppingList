package com.toyyibat.shoppinglist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), ShoppingListFragment.OnShoppingSelected {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, ShoppingListFragment.newInstance(), "shoppingList")
                .commit()
        }
    }

    override fun onShoppingSelected(shoppingModel: ShoppingModel) {
        val detailsFragment =
            ShoppingDetailsFragment.newInstance(shoppingModel)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, "shoppingDetails")
            .addToBackStack(null)
            .commit()

    }
}
