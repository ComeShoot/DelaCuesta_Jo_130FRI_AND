package com.project.basiccalculator

import android.os.Bundle
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.util.SparseBooleanArray
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.toSpannable

class MainActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var editText: EditText
    private lateinit var addButton: Button
    private val items = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var selectedItemPosition = -1

    private lateinit var gestureDetector: GestureDetector

    private val checkedStates = SparseBooleanArray()

    companion object {
        private const val INVALID_POSITION = -1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        setupGestureDetector()
        setupListView()
        setupAddButton()
    }

    private fun initializeViews() {
        listView = findViewById(R.id.listView)
        editText = findViewById(R.id.editText)
        addButton = findViewById(R.id.addButton)
    }

    private fun setupGestureDetector() {
        gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onDoubleTap(e: MotionEvent): Boolean {
                val view = listView.findChildViewUnder(e.x, e.y)
                view?.let { openContextMenu(it) }
                return true
            }
        })
    }

    private fun setupListView() {
        adapter = object : ArrayAdapter<String>(this, R.layout.list_item, R.id.textView, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val checkBox = view.findViewById<CheckBox>(R.id.checkBox)
                val textView = view.findViewById<TextView>(R.id.textView)

                updateCheckBoxAndTextView(position, checkBox, textView)

                view.setOnTouchListener { _, event ->
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
    }

    private fun updateCheckBoxAndTextView(position: Int, checkBox: CheckBox, textView: TextView) {
        val isChecked = checkedStates.get(position, false)
        checkBox.isChecked = isChecked
        textView.text = getStrikethroughText(items[position], isChecked)

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            checkedStates.put(position, isChecked)
            textView.text = getStrikethroughText(items[position], isChecked)
        }
    }

    private fun getStrikethroughText(taskText: String, isChecked: Boolean): CharSequence {
        return if (isChecked) {
            taskText.toSpannable().apply {
                setSpan(StrikethroughSpan(), 0, length, 0)
            }
        } else {
            taskText
        }
    }

    private fun setupAddButton() {
        addButton.setOnClickListener {
            val task = editText.text.toString()
            if (task.isNotEmpty()) {
                items.add(task)
                editText.text.clear()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.edit -> {
                showEditDialog()
                true
            }
            R.id.delete -> {
                deleteSelectedItem()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun showEditDialog() {
        val currentTask = items[selectedItemPosition]

        val editText = EditText(this).apply { setText(currentTask) }

        AlertDialog.Builder(this)
            .setTitle("Edit your To Do List")
            .setMessage("Modify your task")
            .setView(editText)
            .setPositiveButton("Save") { dialog, _ ->
                val newTask = editText.text.toString()
                if (newTask.isNotEmpty()) {
                    items[selectedItemPosition] = newTask
                    adapter.notifyDataSetChanged()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    private fun deleteSelectedItem() {
        items.removeAt(selectedItemPosition)
        checkedStates.delete(selectedItemPosition)
        adapter.notifyDataSetChanged()
    }

    private fun ListView.findChildViewUnder(x: Float, y: Float): View? {
        val position = pointToPosition(x.toInt(), y.toInt())
        return if (position == INVALID_POSITION) null else getChildAt(position - firstVisiblePosition)
    }
}
