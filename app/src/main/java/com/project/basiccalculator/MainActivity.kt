package com.project.basiccalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the default fragment (ProfileFragment in this case)
        loadFragment(ProfileFragment())

        // Set the selected item to the default fragment
        bottomNavigationView.selectedItemId = R.id.nav_profile

        // Handle navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            var fragment: Fragment? = null
            when (item.itemId) {
                R.id.nav_calculator -> fragment = CalculatorFragment()
                R.id.nav_todolist -> fragment = ToDoListFragment()
                R.id.nav_profile -> fragment = ProfileFragment()
            }
            fragment?.let { loadFragment(it) }
            true
        }
    }

    // Load the selected fragment into the fragment_container
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}