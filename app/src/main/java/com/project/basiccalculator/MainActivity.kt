package com.project.basiccalculator

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var hamburgerMenu: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_frame, DefaultFragment())
                .commit()
        }

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        hamburgerMenu = findViewById(R.id.hamburger_menu)

        hamburgerMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_fragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.content_frame, FirstFragment())
                        .commit()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_dialog -> {
                    showDialog()
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_exit -> {
                    // Do nothing; submenu will handle the next click
                    drawerLayout.closeDrawer(GravityCompat.START)
                    true
                }
                R.id.nav_confirm_exit -> {
                    finish()
                    true
                }
                else -> false
            }
        }
    }

    private fun showDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("Dialog Title")
            .setMessage("This is a message")
            .setPositiveButton("Go to Fragment") { dialog, _ ->
                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_frame, FirstFragment())
                    .commit()
                dialog.dismiss()
            }
            .setNegativeButton("Go to Exit") { dialog, _ ->
                drawerLayout.openDrawer(GravityCompat.START)
                dialog.dismiss()
            }
            .create()
            .show()
    }

}