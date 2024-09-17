package com.project.basiccalculator

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, TitleFragment())
                .commit()
        }
    }

    fun showArticleContent(title: String) {
        val description = when (title) {
            "Toyota Supra Mk4" -> getString(R.string.Sup)
            "Buggati Chiron" -> getString(R.string.bugs)
            "Mitsubishi Lancer Evo x" -> getString(R.string.Evo)
            else -> "No available description."
        }

        val imageResId = when (title) {
            "Toyota Supra Mk4" -> R.drawable.supra
            "Buggati Chiron" -> R.drawable.bugs
            "Mitsubishi Lancer Evo x" -> R.drawable.mitus
            else -> R.drawable.ic_launcher_background
        }

        val contentFragment = ContentFragment.newInstance(title, description, imageResId)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_content, contentFragment)
                .commit()
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, contentFragment)
                .addToBackStack(null)
                .commit()
        }
    }

}