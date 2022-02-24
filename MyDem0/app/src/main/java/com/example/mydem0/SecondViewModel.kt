package com.example.mydem0

import android.app.Application
import android.app.Person
import android.content.Context

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// 1
class SecondViewModel(application: Application) :
    AndroidViewModel(application) {
    private val TAG = "MapsViewModel"
    // 2
    private var personmarkDetailsView: LiveData<SecondViewModel.PersonmarkDetailsView>? = null
    private var personRepo: PersonRepo = PersonRepo(
        getApplication())

    fun addPersonfromedit(item: com.example.mydem0.Person) {
        val per = personRepo.createBookmark()
        per.name = item.name

        per.longitude = item.longitude
        per.latitude = item.latitude

        val newId = personRepo.addBookmark(per)
//        image?.let { per.setImage(it, getApplication() )
//            Log.i(TAG, "New viwmodel  wali jgh $it added to the database.")}
        Log.i(TAG, "New Person $newId addedD to the database.")

    }
    data class PersonmarkDetailsView(
        var id: Long? = null,
        var name: String = "",
        var Longitude: Double = 0.0,
        var Latitude : Double = 0.0
    )


    private fun personToPersonkmarkView(person: com.example.mydem0.Person): PersonmarkDetailsView
    {
        return PersonmarkDetailsView(person.id, person.name,  person.longitude, person.latitude)

    }
    // cnvrt live database bkmrk objct to live bookmark view objct
    private fun mapPersonmarkToPersonmarkView(personId: Long) {
        val person = personRepo.getLiveBookmark(personId)
        personmarkDetailsView = Transformations.map(
            person,
            { repoPerson ->
                repoPerson?.let {
                    personToPersonkmarkView(repoPerson)
                }}
        )
    }
    // tother
    fun getPerson(personId: Long): LiveData<PersonmarkDetailsView>? {
        if (personmarkDetailsView == null) {
            mapPersonmarkToPersonmarkView(personId)
        }
        return personmarkDetailsView
    }

    //updating reverse method convrt

    private fun personViewToBookmark(personview: PersonmarkDetailsView):
            com.example.mydem0.Person?
    { val person = personview.id?.let {
        personRepo.getBookmark(it) }

        if (person != null) {
            person.id = personview.id
            person.name = personview.name

            person.longitude = personview.Longitude
            person.latitude = personview.Latitude
        }
        return person
    }

    fun updateBookmark(personview: PersonmarkDetailsView) {
        // 1
        GlobalScope.launch {
//
            val person = personViewToBookmark(personview)// 3
            person?.let{
                personRepo.updateBookmark(it) }
        } }


// deletingggggg

    fun Delete(personviews : PersonmarkDetailsView)
    {
        GlobalScope.launch {
            val bookmark = personviews.id?.let {
                personRepo.getBookmark(it)
            }
            bookmark?.let {
                personRepo.Delete(it)
            }
        }

    }

    //updating image




}


