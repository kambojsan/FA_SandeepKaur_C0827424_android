package com.example.mydem0

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Person::class), version = 1)
abstract class PersonDataBase : RoomDatabase() {
    // 2
    abstract fun personDao(): PersonDao
    // 3
    companion object {
        // 4
        private var instance: PersonDataBase? = null
        // 5
        fun getInstance(context: Context): PersonDataBase {
            if (instance == null) {
                // 6
                instance = Room.databaseBuilder( context.applicationContext, PersonDataBase::class.java, "PlaceBook").build()
            }
// 7
            return instance as PersonDataBase
        }
    } }