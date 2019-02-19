package com.example.owl.todolist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoItemRecyclerView: RecyclerView
    private lateinit var recyclerAdapter: TodoItemsAdapter
    private lateinit var recyclerLayoutManager: RecyclerView.LayoutManager
    private lateinit var todaysItemsButton: Button
    private lateinit var pastItemsButton: Button

    var todoItemsList = ArrayList<TodoItem>()
    var todaysItemsList = ArrayList<TodoItem>()
    var pastItemsList = ArrayList<TodoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dbo = DatabaseOperations(this)
        val cursor = dbo.getAllItems(dbo)
        with(cursor) {
            while(moveToNext()) {
                val itemName = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_NAME))
                val itemUrgency = getInt(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_ITEM_URGENCY))
                val itemDate = getString(getColumnIndex(DatabaseInfo.TableInfo.COLUMN_DATE))
                val isUrgent = if (itemUrgency == 0) false else true

                val newTodoItems = TodoItem(itemName, isUrgent)
                newTodoItems.dateString = itemDate
                todoItemsList.add(newTodoItems)

                if (itemDate == getDateAsString()) {
                    todaysItemsList.add(newTodoItems)
                } else {
                    pastItemsList.add(newTodoItems)
                }
            }
        }

        todoItemRecyclerView = findViewById(R.id.todo_item_recycler_view)
        todaysItemsButton = findViewById(R.id.todays_items_button)
        pastItemsButton = findViewById(R.id.past_items_button)

        recyclerLayoutManager = LinearLayoutManager(this)
        recyclerAdapter = TodoItemsAdapter(todoItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
    }

    public fun displayTodaysItems(view: View) {
        recyclerAdapter = TodoItemsAdapter(todaysItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        todaysItemsButton.background = getDrawable(R.color.pureBlack)
        todaysItemsButton.setTextColor(getColor(R.color.pureWhite))
        pastItemsButton.background = getDrawable(R.color.pureWhite)
        pastItemsButton.setTextColor(getColor(R.color.pureBlack))
    }

    public fun displayPastItems(view: View) {
        recyclerAdapter = TodoItemsAdapter(pastItemsList, this)

        todoItemRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = recyclerLayoutManager
            adapter = recyclerAdapter
        }
        todaysItemsButton.background = getDrawable(R.color.pureWhite)
        todaysItemsButton.setTextColor(getColor(R.color.pureBlack))
        pastItemsButton.background = getDrawable(R.color.pureBlack)
        pastItemsButton.setTextColor(getColor(R.color.pureWhite))
    }

    public fun navToAddItemAction(view: View) {
        val intent: Intent = Intent(this, AddItemActivity::class.java)
        startActivity(intent)
    }

    fun getDateAsString(): String {
        val date = Calendar.getInstance()
        val year = date.get(Calendar.YEAR).toString()
        val month = date.get(Calendar.MONTH).toString()
        val day = date.get(Calendar.DAY_OF_MONTH).toString()
        return "$year/$month/$day"
    }
}
