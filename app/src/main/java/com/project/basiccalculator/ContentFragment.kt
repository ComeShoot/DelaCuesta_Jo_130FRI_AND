package com.project.basiccalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment

class ContentFragment : Fragment() {

    private lateinit var titleTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var articleImageView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_content, container, false)

        titleTextView = view.findViewById(R.id.textViewTitle)
        descriptionTextView = view.findViewById(R.id.textViewDescription)
        articleImageView = view.findViewById(R.id.imageViewArticle)

        val title = arguments?.getString("article_title") ?: "No Title"
        val description = arguments?.getString("article_description") ?: "No Description"
        val imageResId = arguments?.getInt("article_image") ?: R.drawable.ic_launcher_background

        titleTextView.text = title
        descriptionTextView.text = description
        articleImageView.setImageResource(imageResId)

        return view
    }

    companion object {
        fun newInstance(title: String, description: String, imageResId: Int): ContentFragment {
            val fragment = ContentFragment()
            val args = Bundle()
            args.putString("article_title", title)
            args.putString("article_description", description)
            args.putInt("article_image", imageResId)
            fragment.arguments = args
            return fragment
        }
    }
}