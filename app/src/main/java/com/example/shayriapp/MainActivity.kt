package com.example.shayriapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shayriapp.datafile.DataEntity
import com.example.shayriapp.datafile.ExpensesDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var expenseList: ArrayList<DataEntity>
    private lateinit var rvadapter: Adapter
    private lateinit var recyclerview: RecyclerView
    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView and FloatingActionButton
        recyclerview = findViewById(R.id.recyclerview)
        fab = findViewById(R.id.fab)

        expenseList = ArrayList()

        // Setup RecyclerView with Adapter
        rvadapter = Adapter(this, expenseList) { expense ->
            deleteExpense(expense)
        }
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = rvadapter

        // Fetch and display expenses
        loadExpenses()

        // Add new expense
        fab.setOnClickListener {
            startActivity(Intent(this, AddEditActivity::class.java))
        }
    }

    private fun loadExpenses() {
        val expensesDao = ExpensesDatabase.getDatabase(applicationContext).expensesDao()

        GlobalScope.launch(Dispatchers.IO) {
            val expenses = expensesDao.getAllExpenses()

            withContext(Dispatchers.Main) {
                expenseList.clear()
                expenseList.addAll(expenses)
                rvadapter.notifyDataSetChanged()
            }
        }
    }

    private fun deleteExpense(expense: DataEntity) {
        val expensesDao = ExpensesDatabase.getDatabase(applicationContext).expensesDao()

        GlobalScope.launch(Dispatchers.IO) {
            expensesDao.deleteExpense(expense)

            withContext(Dispatchers.Main) {
                expenseList.remove(expense)
                rvadapter.notifyDataSetChanged()
                Toast.makeText(this@MainActivity, "Expense deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload expenses when coming back from AddEditActivity
        loadExpenses()
    }
}
