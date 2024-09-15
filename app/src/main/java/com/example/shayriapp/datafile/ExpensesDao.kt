package com.example.shayriapp.datafile

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface ExpensesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: DataEntity)

    @Delete
    suspend fun deleteExpense(expense: DataEntity)

    @Update
    suspend fun updateExpense(expense: DataEntity)

    @Query("SELECT * FROM expenses_table ORDER BY date DESC")
    suspend fun getAllExpenses(): List<DataEntity>

    @Query("SELECT * FROM expenses_table WHERE id = :id")
    suspend fun getExpenseById(id: Int): DataEntity?
}