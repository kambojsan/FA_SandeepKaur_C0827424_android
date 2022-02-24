package com.example.mydem0

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*

//lateinit  var images  : Bitmap
class SecondActivity : AppCompatActivity() {
    private val TAG = "PersonsViewModel"
    lateinit  var personmarkDetailsView: SecondViewModel.PersonmarkDetailsView
    private lateinit var editViewModel: SecondViewModel

    companion object {

        const val Extra_age = ""
            const val ExtraList = "list"

    }

 //   private lateinit var editViewModel: SecondViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        editViewModel = ViewModelProviders.of(this).get(SecondViewModel::class.java)



        var Intnt : Intent = getIntent()
        if (Intnt.hasExtra(Extra_age)) {
            setTitle("Edit Details")
            getIntentData()
        }
        else{
            setTitle("Add Details")
        }



        //calender
       birthdaypicker()




    }


    private fun getIntentData() {
     //   val personId = intent.getLongExtra(ListActivity.Companion.EXTRA_BOOKMARK_ID, 0)
        val personId = intent.getLongExtra(MapsActivity.Companion.EXTRA_BOOKMARK_ID, 0)
        Log.i(TAG, " id Person $personId is recieved to the database.")

        editViewModel.getPerson(personId)?.observe(this,  androidx.lifecycle.Observer{
            it?.let {

                // onContentChanged()
                personmarkDetailsView = it
                Log.i(TAG, " id Person $it is recieved to the editable.")
                val nameinput = findViewById<EditText>(R.id.editTextName)

                val longinput = findViewById<EditText>(R.id.editLongi)
                val latinput = findViewById<EditText>(R.id.editLati)

              //  val imugu = it.getImage(this)
                nameinput.setText(it.name)

                longinput.setText(it.Longitude.toString())
                latinput.setText(it.Latitude.toString())

              //  ima.setImageBitmap(imugu)
            }
        })
    }
// menu working delete and saving and updating

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.save, menu)
        return true;
    }


















    // photossssssssssss dailogggggggggggggggg

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
            super.onActivityResult(requestCode, resultCode, data) // 1





                }








    // menu


    override fun onOptionsItemSelected(item: MenuItem): Boolean { // setupViewModel()
        var id = item.itemId

        if (id == R.id.item1) {
            var Intnt : Intent = getIntent()
            if (Intnt.hasExtra(Extra_age))
            {
               GlobalScope.launch {
                    saveChanges()
                    Log.i(TAG, "t5  Person  is updated to the database.")
                }
                return true
                finish()

            }
            else {

                val nameinput = findViewById<EditText>(R.id.editTextName)

                val longinput = findViewById<EditText>(R.id.editLongi)
                val latinput = findViewById<EditText>(R.id.editLati)

              GlobalScope.launch {
                    var joh: Person = Person(

                        name = nameinput.text.toString(),
                        longitude = longinput.text.toString().toDouble(),
                        latitude = latinput.text.toString().toDouble()

                       )
                  editViewModel.addPersonfromedit(joh)


                    Log.i(TAG, "New ho gya ee $joh addedd dto the database.")
                }
                finish()
               }
            }
        if (id == R.id.item2){

            deleteBookmark()
            return true
            Log.i(TAG, "t5  Person  is katya gya e to the database.")
            finish()

        }


        return super.onOptionsItemSelected(item)
    }




        private fun saveChanges() {
            val nameinput = findViewById<EditText>(R.id.editTextName)

            val longinput = findViewById<EditText>(R.id.editLongi)
            val latinput = findViewById<EditText>(R.id.editLati)
            val name = nameinput.text.toString()
            if (name.isEmpty())
            {
                return
            }
            personmarkDetailsView?.let { personView ->
                personView.name = nameinput.text.toString()

                personView.Longitude = longinput.text.toString().toDouble()
                personView.Latitude = latinput.text.toString() .toDouble()
                editViewModel.updateBookmark(personView)
            }
            finish()
        }

        private fun deleteBookmark()
        {
            val personvieww = personmarkDetailsView ?: return

            AlertDialog.Builder(this)
                .setMessage("Delete?")
                .setPositiveButton("Ok") { _, _ ->
                    editViewModel.Delete(personvieww)
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }























        fun birthdaypicker()
 {   var c = Calendar.getInstance()
     val year = c.get(Calendar.YEAR)
     val month = c.get(Calendar.MONTH)
     val day = c.get(Calendar.DAY_OF_MONTH)
     SelectBirthday.setOnClickListener {
         val datee = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view: DatePicker, m: Int, month: Int, dayOfMonth: Int ->
             // set to text
             var birthdayviews = findViewById<TextView>(R.id.birthdayview)
             birthdayviews.setText("" + dayOfMonth + "/" + month + "/" + m)
         }, year, month, day)
         datee.show()
     }}

}

