package com.example.ecommerceproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.ecommerceproject.data.Product
import com.example.ecommerceproject.data.User
import com.example.ecommerceproject.data.dao.EcommerceDao

@Database(entities = [Product::class, User::class], version = 4, exportSchema = false)
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