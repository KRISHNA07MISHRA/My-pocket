package com.example.shayriapp.datafile

import java.util.Date
import java.util.GregorianCalendar

object SampleData {

    // Function to generate a sample date with an offset in days from today
    fun getDate(diff: Int): Date {
        val calendar = GregorianCalendar()
        calendar.add(GregorianCalendar.DATE, diff)
        return calendar.time
    }

    // Function to generate a sample list of DataEntity objects
    val data: List<DataEntity>
        get() {
            val list = mutableListOf<DataEntity>()

            // Add sample data to the list
            list.add(DataEntity(1, getDate(0), 100.0, "Food", "Lunch at restaurant"))
            list.add(DataEntity(2, getDate(-1), 50.0, "Transport", "Bus fare"))
            list.add(DataEntity(3, getDate(-2), 200.0, "Rent", "Monthly rent payment"))
            list.add(DataEntity(4, getDate(-3), 75.0, "Groceries", "Grocery shopping"))
            list.add(DataEntity(5, getDate(-4), 120.0, "Entertainment", "Movie tickets"))
            list.add(DataEntity(4, getDate(-3), 75.0, "Groceries", "Grocery shopping"))
            list.add(DataEntity(5, getDate(-4), 120.0, "Entertainment", "Movie tickets"))

            return list
        }
}
