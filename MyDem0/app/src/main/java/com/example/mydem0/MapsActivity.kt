package com.example.mydem0


//import kotlinx.android.synthetic.main.main_view_maps.*
import android.Manifest
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProviders
import com.example.mydem0.Adapter.PersonmarkInfoAdapter
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.main_view_maps.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import androidx.annotation.RequiresApi as RequiresApi1

class MapsActivity : AppCompatActivity(), OnMapReadyCallback , NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener

{
    private lateinit var editViewModel: MapViewModel
    private lateinit var placesClient: PlacesClient
    private lateinit var fusedLocationClient: FusedLocationProviderClient
  //  private var locationRequest: LocationRequest? = null

    companion object {
        const val EXTRA_BOOKMARK_ID = "com.raywenderlich.placebook.EXTRA_BOOKMARK_ID"
        private const val REQUEST_LOCATION = 1
        private const val TAG = "MapsActivity"
        private const val AUTOCOMPLETE_REQUEST_CODE = 2
    }

    private val TAG = "PersonsViewModel"
    private lateinit var mMap: GoogleMap

lateinit var personViewModel : PersonsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        editViewModel = ViewModelProviders.of(this).get(MapViewModel::class.java)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        // search view.....
        val butsearch: Button = findViewById(R.id.searchbut)
        butsearch.setOnClickListener(View.OnClickListener {
            searchcreateBookmarkMarkerObserver(it)
                Toast.makeText(this, "it", Toast.LENGTH_SHORT).show()

        })

            setupToolbar()
        var navview : NavigationView = findViewById(R.id.nav_view)
        navview.setNavigationItemSelectedListener(this)


        setupLocationClient()
        setupPlacesClient()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        getCurrentLocation()
     //  mMap.setInfoWindowAdapter(PersonmarkInfoAdapter(this))
    //     Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        setupMapListeners()
       //  setupViewModel()






    }
    var addressList : List<Address>? = null ;
    var names : String = "";
    fun searchcreateBookmarkMarkerObserver(view: View)
    {
        val locationsearch: EditText = findViewById(R.id.etxtSearch)



      val  location  = locationsearch.text.toString()
      names = location

        if(location ==null || location == ("" )) { }
        else {
            val geocoder = Geocoder(this)
            try {
                addressList = geocoder.getFromLocationName(location, 1)

            } catch (e: IOException) {
              //  Toast.makeText(this, location, Toast.LENGTH_SHORT).show()

            }
            val address = addressList!![0]
            val latLng = LatLng(address.latitude, address.longitude)
            mMap!!.addMarker(MarkerOptions().position(latLng).title(location))
            mMap!!.animateCamera(CameraUpdateFactory.newLatLng(latLng))
            Toast.makeText(this, location, Toast.LENGTH_SHORT).show()
            Log.i(TAG, "fdgg $location " )

            Log.i(TAG, "fdgg $latLng " )


        }

        }







    private fun updateMapToLocation(location: Location) {
        val latLng = LatLng(location.latitude, location.longitude)
        mMap.animateCamera(
            CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
        )

    }






    private fun setupPlacesClient() {
        Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        placesClient = Places.createClient(this);
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray) {
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.size == 1 && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Log.e(TAG, "Location permission denied")
            }}}


    private fun setupLocationClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }


    private fun requestLocationPermissions() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION)
    }

    private fun getCurrentLocation() {
        // 1
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
// 2
            requestLocationPermissions()
        }

// 5

    else {
// 3
            mMap.isMyLocationEnabled = true
            fusedLocationClient.lastLocation.addOnCompleteListener {
                val location = it.result
                if (location != null) {
// 4
                    val latLng = LatLng(location.latitude, location.longitude)
                    // 5

                    // 6
                    val update = CameraUpdateFactory.newLatLngZoom(latLng, 16.0f)
                    // 7
                    mMap.moveCamera(update)
                } else { // 8
                    Log.e(TAG, "No location found")
                }
            } }
    }




























//
    private fun addPlaceMarker( person: PersonsViewModel.BookmarkView): Marker? {
        //adds a single blue marker to the map
    Log.i(ContentValues.TAG, "vekhi ki onda pala Person $person added to the database.")
    Toast.makeText(this, "$person", Toast.LENGTH_SHORT).show()
        val marker = mMap.addMarker( MarkerOptions()
            .position(person.location)
            .title(person.name)
            .snippet(person.gender))

        marker.tag = person

        return marker

    }









//    personViewModel.getAllDataByName(text)?.observe(this,  androidx.lifecycle.Observer{
//
//
//            mMap.clear()
//            if (!it.isNullOrEmpty()) {
//                for (data in it) {
//                //update the map data
//                Toast.makeText(this, "$data", Toast.LENGTH_SHORT).show()
//                val marker = mMap.addMarker( MarkerOptions()
//                        .position(LatLng(data.latitude, data.longitude))
//                        .title(data.name)
//                        .snippet(data.gender))
//
//                marker.tag = data
//                    mMap.moveCamera(
//                            CameraUpdateFactory.newLatLng(
//                                    LatLng(
//                                            it[it.lastIndex].latitude,
//                                            it[it.lastIndex].longitude
//                                    )
//                            )
//                    )

          //      }
//}
//            else {
//                Toast.makeText(this, "Data Not Found", Toast.LENGTH_SHORT).show()
//                }
//
//        })














    private fun handleInfoWindowClick(marker: Marker) {

     // marker.position
           // starteditmarkDetails(it)
            Log.i(TAG, "New  addedXX to the database.")
        val address = addressList!![0]
        val latLng = LatLng(address.latitude, address.longitude)


        Log.i(TAG, "fdgg $latLng " )
        Log.i(TAG, "fdgg ${latLng.latitude} " )
        Log.i(TAG, "fdgg $addressList " )

        GlobalScope.launch {
            var joh: Person = Person(

                name = names,
                longitude = latLng.longitude,
                latitude = latLng.latitude

            )
            editViewModel.addPersonfromedit(joh)


            Log.i(TAG, "New ho gya ee $joh addedd dto the database.")
        }
    }

    private fun setupMapListeners() {
       // mMap.setInfoWindowAdapter(PersonmarkInfoAdapter(this))
        mMap.setOnInfoWindowClickListener {
            handleInfoWindowClick(it)
        }
    }


















    private fun setupToolbar()
        {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this,drawerLayout,toolbar, R.string.open_drawer, R.string.close_drawer)
        toggle.syncState()
          }
    @RequiresApi1(Build.VERSION_CODES.LOLLIPOP)
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.nav_List)
        {
             val intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
             finishAfterTransition()
        }
        else
        {
            return true
        }

        return true
    }


    override fun onConnected(p0: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun onConnectionSuspended(p0: Int) {
        TODO("Not yet implemented")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        TODO("Not yet implemented")
    }

}




