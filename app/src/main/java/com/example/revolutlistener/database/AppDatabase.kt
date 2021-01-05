package com.example.revolutlistener.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [AmountTable::class, Spend::class, Budget::class], version = 6, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val ledger: LedgerDao
    abstract val amountDao: AmountDao
    abstract val spendDao: SpendDao
    abstract val budgetDao: BudgetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "spend_history_database"
                    )
                            .fallbackToDestructiveMigration()
                            .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}