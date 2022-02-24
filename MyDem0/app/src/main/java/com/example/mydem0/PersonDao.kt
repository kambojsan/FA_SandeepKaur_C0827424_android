package com.example.mydem0

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDao {
    // 2
    @Query("SELECT * FROM Person")
    fun loadAll(): LiveData<List<Person>>
    // 3
    @Query("SELECT * FROM Person WHERE id = :bookmarkId")
    fun loadperson(bookmarkId: Long): Person
    @Query("SELECT * FROM Person WHERE id = :bookmarkId")
    fun loadLivePerson(bookmarkId: Long): LiveData<Person>
    //searchng one
    @Query("SELECT * FROM Person WHERE Person.name LIKE '%' || (:name) || '%'")
     fun getEntriesByName(name: String): List<Person>

    // 4
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBookmark(bookmark: Person): Long
    // 5
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePerson(bookmark: Person)
    // 6
    @Delete
    fun deletePerson(bookmark: Person)
}