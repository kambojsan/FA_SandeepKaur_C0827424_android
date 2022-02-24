package com.example.mydem0

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.example.mydem0.util.ImageUtils
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.internal.it
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PersonsViewModel(application: Application) :
    AndroidViewModel(application)
{
// foer search
//private var personmarkDetailsView: LiveData<List<PersonsViewModel.PersonmarkDetailsView>>? = null


    private val TAG = "PersonsViewModel"
    // 2
    private var personRepo: PersonRepo = PersonRepo(getApplication())
    val getperson: LiveData<List<Person>> =  personRepo.allBookmarks
    // 3
    private var bookmarks: LiveData<List<BookmarkView>>? = null
    data class BookmarkView(
        var id: Long? = null,
        var location: LatLng = LatLng(0.0, 0.0),
        var name: String = "",
        var gender: String = "",
        var country: String = "")
    {  //1 for get category icon
//        fun getImage(context: Context): Bitmap? {
//            id?.let {
//               // val imageName = Person.generateImageFilename(it)
//              //  return ImageUtils.loadBitmapFromFile(context, imageName)
//            }
//            return null
//        }

    }

    private fun bookmarkToBookMarkView(bookmark: Person): PersonsViewModel.BookmarkView {
        return BookmarkView(
            bookmark.id,
            LatLng(bookmark.latitude, bookmark.longitude),
            bookmark.name,
            bookmark.gender,
            bookmark.country)
    }

    //2 convert many
    private fun mapBookmarksToBookmarkView() {
        // 1
        bookmarks = Transformations.map(personRepo.allBookmarks) { repoBookmarks ->
            // 2
            repoBookmarks.map {
                    bookmark -> bookmarkToBookMarkView(bookmark)
            }
        }
    }

    //1 data returned will be observed
    fun getBookmarkViews(): LiveData<List<BookmarkView>>? {
        if (bookmarks == null) {
            mapBookmarksToBookmarkView()
        }
        return bookmarks
    }

//searchinggg

    fun getAllDataByName(name: String): MutableLiveData<List<Person>?> {
        val dataLiveData = MutableLiveData<List<Person>?>()
        viewModelScope.launch(Dispatchers.IO) {
            val person = personRepo.getLiveBookmarkname(name)
            viewModelScope.launch(Dispatchers.Main) { dataLiveData.value = person }
        }

        return dataLiveData
    }
}