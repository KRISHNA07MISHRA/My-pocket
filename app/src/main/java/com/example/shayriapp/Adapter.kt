package com.example.shayriapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shayriapp.datafile.DataEntity
import com.example.shayriapp.databinding.ViewmodelBinding

class Adapter(
    private val context: Context,
    private val expenseList: List<DataEntity>,
    private val deleteCallback: (DataEntity) -> Unit
) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    // ViewHolder class with ViewBinding
    class ViewHolder(val binding: ViewmodelBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflate the layout using ViewBinding
        val binding = ViewmodelBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return expenseList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Bind data to views
        val currentExpense = expenseList[position]
        holder.binding.textViewDate.text = currentExpense.date.toString() // Format date as needed
        holder.binding.textViewAmount.text = context.getString(R.string.amount_format, currentExpense.amount) // Assume there's a format in strings.xml
        holder.binding.textViewCategory.text = currentExpense.category
        holder.binding.textViewDescription.text = currentExpense.description

        // Handle edit button click
        holder.binding.editButton.setOnClickListener {
            val intent = Intent(context, AddEditActivity::class.java)
            intent.putExtra("expense_id", currentExpense.id) // Pass the expense ID to the AddEditActivity
            context.startActivity(intent)
        }

        // Handle delete button click
        holder.binding.delete.setOnClickListener {
            deleteCallback(currentExpense)
        }
    }
}
