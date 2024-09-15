package com.example.shayriapp.datafile


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.shayriapp.DateConverter

@Database(entities = [DataEntity::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ExpensesDatabase : RoomDatabase() {

    abstract fun expensesDao(): ExpensesDao

    companion object {
        @Volatile
        private var INSTANCE: ExpensesDatabase? = null

        fun getDatabase(context: Context): ExpensesDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ExpensesDatabase::class.java,
                    "expenses_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
