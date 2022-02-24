package com.example.mydem0

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.internal.it
import com.google.android.material.navigation.NavigationView
import com.google.common.base.Strings.isNullOrEmpty
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.main_view_list.*
import kotlinx.android.synthetic.main.main_view_maps.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, recylerviewAdapter.OnClickLister
{

    var intkey : Int = 0


    companion object {

        const val Start_REQUEST_CODE = "ghccghcg"
    }
    var Lists : List<PersonListviewModel.BookmarkView >  = listOf(PersonListviewModel.BookmarkView())
    var List : List<Person>   = emptyList()
    private val TAG = "MainActivity"
    lateinit var listsRecyclerView: RecyclerView
    lateinit var searchlistsRecyclerView: RecyclerView
    lateinit var personViewModel: PersonListviewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setupToolbar()
        setTitle("List View")

        var navview : NavigationView = findViewById(R.id.nav_view)
        navview.setNavigationItemSelectedListener(this)
        Floatbutn()
        personViewModel = ViewModelProviders.of(this).get(PersonListviewModel::class.java)

        searchViewlist.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
               addlist(intkey)
                } else if (!newText.isNullOrEmpty()) {
                    searchaddlist(newText)
                }
                return true
            }
        })


//        var item = object : SwipeToDelete(this,0, ItemTouchHelper.LEFT) {
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
//            {
//                val recyclerAdapter = listsRecyclerView.adapter as recylerviewAdapter
//                GlobalScope.launch {
//                    recyclerAdapter.deletePerson(viewHolder.adapterPosition)
//
//                    personViewModel.Delete(recyclerAdapter.deletePerson(viewHolder.adapterPosition))
//                    //  Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
//                }
//            }}
//
//        val itemTouchHelper = ItemTouchHelper(item)
//        itemTouchHelper.attachToRecyclerView(listsRecyclerView)

    }


    fun addlist( text : Int)
    { var text  = null
        intkey = 0
        listsRecyclerView = findViewById(R.id.recylerview1)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        setTitle("List of Employee")
        //personViewModel.getperson.observe(this, Observer
        personViewModel.getBookmarkViews()?.observe(
            this, androidx.lifecycle.Observer<List<PersonListviewModel.BookmarkView>> { user ->
                Lists = user
                var recyclerAdapter = listsRecyclerView.adapter as recylerviewAdapter

                recyclerAdapter.Setvalue(List,Lists)

                Toast.makeText(this, "$Lists", Toast.LENGTH_SHORT).show()
                Log.i(ContentValues.TAG, "New Person $Lists added to the database.")

            })
        listsRecyclerView.adapter = recylerviewAdapter(intkey,text,List,Lists,this,this)
    }

    // when search text
    override fun onResume() {

        super.onResume()
        addlist(intkey)
       }
    fun searchaddlist(text : String) {
        listsRecyclerView = findViewById(R.id.recylerview1)
        listsRecyclerView.layoutManager = LinearLayoutManager(this)
        setTitle("List of Employee")
        //personViewModel.getperson.observe(this, Observer
        personViewModel.getAllDataByName(text)?.observe(this, androidx.lifecycle.Observer {
            if (!it.isNullOrEmpty()) {
               List = it
                var recyclerAdapter = listsRecyclerView.adapter as recylerviewAdapter
//
               recyclerAdapter.Setvalue(List, Lists)
//
                Toast.makeText(this, "$List", Toast.LENGTH_SHORT).show()
//                Log.i(ContentValues.TAG, "searching wala $List added to the database.")
               intkey = 0
            }
            else
            {
            intkey = 1

                Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
              //  searchlistsRecyclerView = findViewById(R.id.recylerview1)
              //  searchlistsRecyclerView.layoutManager = LinearLayoutManager(this)
              //  var recyclerAdapter = listsRecyclerView.adapter as ListrecylerviewAdapter
              //  searchlistsRecyclerView.adapter = ListrecylerviewAdapter()


            }
        })
        listsRecyclerView.adapter = recylerviewAdapter(intkey,text, List, Lists, this, this)





    }















    // navigation setup
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.nav_Map)
        {
            Toast.makeText(this, "huaaah ji", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
            finish()
        }

        return true
    }

    override fun onItemClick(person: PersonListviewModel.BookmarkView, position: Int) {
        val intent = Intent(this, SecondActivity::class.java)
         val personId = person.id
        intent.putExtra(SecondActivity.ExtraList,true)
        intent.putExtra(Start_REQUEST_CODE, personId)
        startActivity(intent)
        intent.putExtra(SecondActivity.Extra_age,true)
        intent.putExtra(MapsActivity.EXTRA_BOOKMARK_ID, personId)
        startActivity(intent)
//        intent.putExtra(SecondActivity.EXTRA_Name,person.name)
//        intent.putExtra(editActivity.EXTRA_age,person.age)
//        intent.putExtra(editActivity.EXTRA_fees,person.fees)
//        intent.putExtra(editActivity.EXTRA_ID,person.id)
//        startActivityForResult(intent, Start_REQUEST_CODE)
        Toast.makeText(this, "${person.name}", Toast.LENGTH_SHORT).show()

    }








//


























   // ******************* GENERALllllllllll SetUP

   private fun setupToolbar()
    {
        setSupportActionBar(toolbar1)
      val toggle = ActionBarDrawerToggle(this,drawerLayoutlist,toolbar1, R.string.open_drawer, R.string.close_drawer)
        toggle.syncState()
    }



    fun Floatbutn()
    {

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "huaaah ji", Toast.LENGTH_SHORT).show()
        }
    }
}