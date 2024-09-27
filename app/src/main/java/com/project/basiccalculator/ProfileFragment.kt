package com.project.basiccalculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


class ProfileFragment : Fragment() {
    private lateinit var editName: EditText
    private lateinit var editAge: EditText
    private lateinit var radioGender: RadioGroup
    private lateinit var checkBoxNotifications: CheckBox
    private lateinit var btnSave: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize the views
        editName = view.findViewById(R.id.edit_name)
        editAge = view.findViewById(R.id.edit_age)
        radioGender = view.findViewById(R.id.radio_gender)
        checkBoxNotifications = view.findViewById(R.id.checkbox_notifications)
        btnSave = view.findViewById(R.id.btn_save)

        // Set click listener for the save button
        btnSave.setOnClickListener {
            clearInputs()
            showToast("Information Saved")
        }

        return view
    }

    private fun clearInputs() {
        // Clear the EditText fields
        editName.text.clear()
        editAge.text.clear()

        // Clear the RadioGroup selection
        radioGender.clearCheck()

        // Uncheck the CheckBox
        checkBoxNotifications.isChecked = false
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}