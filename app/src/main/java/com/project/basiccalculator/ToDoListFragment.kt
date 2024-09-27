package com.project.basiccalculator

import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.SparseBooleanArray
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.text.toSpannable


class ToDoListFragment : Fragment() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemPosition = -1
    private lateinit var gestureDetector: GestureDetector
    private val checkedStates = SparseBooleanArray() // To track CheckBox states

    private companion object {
        private const val INVALID_POSITION = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_to_do_list, container, false)

        listView = view.findViewById(R.id.listView)
        editText = view.findViewById(R.id.editText)
        addButton = view.findViewById(R.id.addButton)

        // Create GestureDetector
        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                // Handle double-tap
                val tappedView = listView.findChildViewUnder(e.x, e.y)
                tappedView?.let {
                    it.showContextMenu() // Trigger context menu for the tapped view
                }
                return true
            }
        })

        // Setup Adapter
        adapter = object : ArrayAdapter<String>(requireContext(), R.layout.list_item, R.id.textView, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                // Retrieve CheckBox and set its checked state
                val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
                val textView = view.findViewById<TextView>(R.id.textView)
                val isChecked = checkedStates.get(position, false)

                checkBox.isChecked = isChecked

                // Apply strikethrough if checked
                val taskText = items[position]
                textView.text = if (isChecked) {
                    taskText.toSpannable().apply {
                        setSpan(StrikethroughSpan(), 0, length, 0)
                    }
                } else {
                    taskText
                }

                checkBox.setOnCheckedChangeListener { _, isChecked ->
                    checkedStates.put(position, isChecked)
                    // Update text view strikethrough based on the CheckBox state
                    textView.text = if (isChecked) {
                        taskText.toSpannable().apply {
                            setSpan(StrikethroughSpan(), 0, length, 0)
                        }
                    } else {
                        taskText
                    }
                }

                view.setOnTouchListener { v, event ->
                    // Handle gestures and clicks
                    if (event.action == MotionEvent.ACTION_DOWN) {
                        selectedItemPosition = position
                    }
                    gestureDetector.onTouchEvent(event)
                    true
                }

                return view
            }
        }

        listView.adapter = adapter
        registerForContextMenu(listView)

        addButton.setOnClickListener {
            val task = editText.text.toString()
            if (task.isNotEmpty()) {
                items.add(task)
                editText.text.clear()
                adapter.notifyDataSetChanged()
            }
        }

        return view
    }

    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = requireActivity().menuInflater
        inflater.inflate(R.menu.context_menu, menu) // Inflate the context menu
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                showEditDialog()
                true
            }
            R.id.delete -> {
                if (selectedItemPosition != INVALID_POSITION) { // Check valid position
                    items.removeAt(selectedItemPosition)
                    checkedStates.delete(selectedItemPosition) // Remove the checked state
                    adapter.notifyDataSetChanged()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showEditDialog() {
        if (selectedItemPosition != INVALID_POSITION) { // Check valid position
            val currentTask = items[selectedItemPosition]

            val editText = EditText(requireContext())
            editText.setText(currentTask)

            AlertDialog.Builder(requireContext())
                .setTitle("Edit Task")
                .setMessage("Modify the task")
                .setView(editText)
                .setPositiveButton("Save") { dialog, _ ->
                    val newTask = editText.text.toString()
                    if (newTask.isNotEmpty()) {
                        items[selectedItemPosition] = newTask
                        adapter.notifyDataSetChanged()
                    }
                    dialog.dismiss()
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .create()
                .show()
        }
    }

    private fun ListView.findChildViewUnder(x: Float, y: Float): View? {
        val position = pointToPosition(x.toInt(), y.toInt())
        return if (position == INVALID_POSITION) null else getChildAt(position - firstVisiblePosition)
    }
}