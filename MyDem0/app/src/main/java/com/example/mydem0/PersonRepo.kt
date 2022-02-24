package com.example.mydem0

import android.content.Context
import androidx.lifecycle.LiveData
// 311
class PersonRepo(val context: Context)
{

    // 2
    private var db = PersonDataBase.getInstance(context)
    private var personDao: PersonDao = db.personDao() // 3
    fun addBookmark(bookmark: Person): Long? {
        val newId = personDao.insertBookmark(bookmark)
        bookmark.id = newId
                return newId
    }
    // 4
    fun createBookmark(): Person {
        return Person()
    }
    // 5
    val allBookmarks: LiveData<List<Person>>
        get() {
            return personDao.loadAll() }




    fun updateBookmark(person: Person)
    {

        personDao.updatePerson(person)
    }

    fun getBookmark(bookmarkId: Long): Person {
        return personDao.loadperson(bookmarkId)
    }



    fun getLiveBookmark(bookmarkId: Long): LiveData<Person>
    {
         val person = personDao.loadLivePerson(bookmarkId)
          return person
    }
// ssearch barr
    fun getLiveBookmarkname(name : String): List<Person>
    {
        val person = personDao.getEntriesByName(name)
        return person
    }



    fun Delete(person: Person)
    {
       // person.deleteImage(context )
        personDao.deletePerson(person)


    }




}