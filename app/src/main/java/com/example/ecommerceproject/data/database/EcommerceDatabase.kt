package com.example.ecommerceproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerceproject.data.*
import com.example.ecommerceproject.data.dao.EcommerceDao

@Database(entities = [Product::class, User::class, Address::class, CreditCard::class, CurrentAddress::class], version = 3, exportSchema = false)
abstract class EcommerceDatabase : RoomDatabase() {

    abstract val ecommerceDao : EcommerceDao

    companion object{
        var INSTANCE : EcommerceDatabase? = null

        fun getInstance(context: Context) : EcommerceDatabase{
            if(INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, EcommerceDatabase::class.java,"EcommerceDatabase")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            INSTANCE?.let{
                return it
            }
            }
            return INSTANCE as EcommerceDatabase
        }
    }
}