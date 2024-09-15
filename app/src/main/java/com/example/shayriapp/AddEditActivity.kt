package com.example.shayriapp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shayriapp.datafile.DataEntity
import com.example.shayriapp.datafile.ExpensesDao
import com.example.shayriapp.datafile.ExpensesDatabase
import com.example.shayriapp.datafile.SampleData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.shayriapp.databinding.ActivityAddEditBinding

class AddEditActivity : AppCompatActivity() {

    private lateinit var expensesDao: ExpensesDao
    private lateinit var binding: ActivityAddEditBinding
    private var expenseId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Room DAO
        val database = ExpensesDatabase.getDatabase(this)
        expensesDao = database.expensesDao()

        // Get the expense ID from the Intent
        expenseId = intent.getIntExtra("expense_id", -1)

        if (expenseId != -1) {
            // Load the expense data for editing
            loadExpenseData(expenseId!!)
        }

        // Handle Save button click
        binding.btnSave.setOnClickListener {
            val description = binding.etDescription.text.toString()
            val amount = binding.etAmount.text.toString().toDoubleOrNull()
            val category = binding.etCatogory.text.toString()
            //val date = binding.etDate.text.toString()

            if (description.isNotEmpty() && amount != null) {
                if (expenseId != null && expenseId != -1) {
                    // Update existing expense
                    val expense = DataEntity(expenseId!!, SampleData.getDate(0), amount, description, category)
                    GlobalScope.launch(Dispatchers.IO) {
                        expensesDao.updateExpense(expense)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddEditActivity, "Expense updated", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                } else {
                    // Add new expense
                    val expense = DataEntity(0, SampleData.getDate(0), amount, description, category)
                    GlobalScope.launch(Dispatchers.IO) {
                        expensesDao.insertExpense(expense)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddEditActivity, "Expense added", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all details", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadExpenseData(id: Int) {
        GlobalScope.launch(Dispatchers.IO) {
            val expense = expensesDao.getExpenseById(id)
            withContext(Dispatchers.Main) {
                if (expense != null) {
                    binding.etDescription.setText(expense.description)
                    binding.etAmount.setText(expense.amount.toString())
                    binding.etCatogory.setText(expense.category)
                    //binding.etDate.setText(expense.date.toString())
                } else {
                    Toast.makeText(this@AddEditActivity, "Expense not found", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
