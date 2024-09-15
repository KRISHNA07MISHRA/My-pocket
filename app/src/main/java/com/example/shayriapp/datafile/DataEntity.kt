package com.example.shayriapp.datafile

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "expenses_table")
data class DataEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "Date")
    var date: Date,

    @ColumnInfo(name = "Amount")
    var amount: Double,

    @ColumnInfo(name = "Category")
    var category: String,

    @ColumnInfo(name = "Description")
    var description: String
)
