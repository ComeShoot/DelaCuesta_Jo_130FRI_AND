package com.project.basiccalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class TitleFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TitleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title_list, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        // Dummy list of article titles
        val titles = listOf("Toyota Supra Mk4", "Buggati Chiron", "Mitsubishi Lancer Evo x")
        adapter = TitleAdapter(titles) { title ->
            (activity as MainActivity).showArticleContent(title)
        }

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return view
    }
}