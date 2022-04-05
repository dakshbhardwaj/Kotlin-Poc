package com.example.findmyage

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.findmyage.models.WeatherResponse
import com.example.findmyage.network.WeatherService
import com.google.android.gms.location.*
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*


class Weather : AppCompatActivity() {
    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient;
    private var mProgressDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        if (!isLocationEnabled()) {
            Toast.makeText(this, "Please Turn on the location", Toast.LENGTH_LONG).show();
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent)
        } else {
            Dexter.withContext(this)
                .withPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {

                        if (report!!.areAllPermissionsGranted()) {
                            requestLocationData()
                        }
                        if (report.isAnyPermissionPermanentlyDenied) {
                            Toast.makeText(
                                this@Weather,
                                " Location is turned off",
                                Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissions: MutableList<PermissionRequest>?,
                        token: PermissionToken?
                    ) {
                        showRationalDialogueForPermission()
                    }
                }).onSameThread().check()
        }
    }


    @SuppressLint("MissingPermission")
    private fun requestLocationData() {

        val mLocationRequest = LocationRequest();
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY;

        mFusedLocationProviderClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback,
            Looper.myLooper()
        )

    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            val mLastLocation: Location? = locationResult?.lastLocation;
            val latitude = mLastLocation?.latitude;
            Log.i("Current latitutde", "$latitude")

            val longitude = mLastLocation?.longitude;
            Log.i("Current latitutde", "$longitude")

            if (latitude != null && longitude != null) {
                getLocationWeatherDetails(latitude, longitude)
            }
        }
    }

    private fun showRationalDialogueForPermission() {
        AlertDialog.Builder(this)
            .setMessage("It's look like location permission is turned off")
            .setPositiveButton("Go To Settings") { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    val uri = Uri.fromParts("package", packageName, null);
                    intent.data = uri;
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { Dialog, _ -> Dialog.dismiss() }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun getLocationWeatherDetails(latitude: Double, longitude: Double) {
        if (Constants.isNetWorkAvailable(this)) {
            Log.i("Internet is connected", "$latitude, $longitude")

            val retrofit: Retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
            val service: WeatherService =
                retrofit.create<WeatherService>(WeatherService::class.java);

            val listCall: Call<WeatherResponse> =
                service.getWeather(latitude, longitude, Constants.METRIC_UNIT, Constants.API_ID)

            showCustomDialog();

            listCall.enqueue(object : Callback<WeatherResponse> {
                override fun onResponse(
                    call: Call<WeatherResponse>,
                    response: Response<WeatherResponse>
                ) {
                    if (response.isSuccessful) {

                        val weatherList: WeatherResponse? = response.body()
                        if (weatherList != null) {
                            setUpUi(weatherList)
                        }
                        hideCustomDialog();
                        Log.i("result", "$weatherList");
                    } else {
                        when (response.code()) {
                            400 -> {
                                Log.i("Error 400", "Bad Connection ${response}");
                            }
                            else -> {
                                Log.i("Error ", "Generic error");
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                    hideCustomDialog();
                    Log.i("Error ${t.message}", t!!.message.toString());
                }


            })
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
        }
    }

    private fun showCustomDialog() {
        mProgressDialog = Dialog(this)

        mProgressDialog!!.setContentView(R.layout.custom_progress)

        mProgressDialog!!.show();
    }

    private fun hideCustomDialog() {
        if (mProgressDialog != null) {
            mProgressDialog!!.dismiss()
        }
    }

    private fun setUpUi(weatherList: WeatherResponse) {
        for (i in weatherList.weather.indices) {
            Log.i("weather Name", weatherList.weather.toString())
            findViewById<TextView>(R.id.tv_main).text = weatherList.weather[i].main;
            findViewById<TextView>(R.id.tv_main_description).text =
                weatherList.weather[i].description;
            findViewById<TextView>(R.id.tv_temp).text =
                weatherList.main.temp.toString() + getUnit(application.resources.configuration.toString())
            findViewById<TextView>(R.id.tv_sunrise_time).text = unixTime(weatherList.sys.sunrise)
            findViewById<TextView>(R.id.tv_sunset_time).text = unixTime(weatherList.sys.sunset)
            findViewById<TextView>(R.id.tv_humidity).text =
                weatherList.main.humidity.toString() + "per cent"
            findViewById<TextView>(R.id.tv_min).text =
                weatherList.main.temp_min.toString() + " min";
            findViewById<TextView>(R.id.tv_max).text =
                weatherList.main.temp_max.toString() + " max";
            findViewById<TextView>(R.id.tv_speed).text = weatherList.wind.speed.toString();
            findViewById<TextView>(R.id.tv_name).text = weatherList.name
            findViewById<TextView>(R.id.tv_country).text = weatherList.sys.country

        }
    }

    private fun getUnit(value: String): String? {
        var value = "°C"
        if ("US" == value || "LR" == value || "MM" == value) {
            value = "°F";
        }
        return value;
    }

    private fun unixTime(timex: Long): String? {
        val date = Date(timex * 1000L);
        val sdf = SimpleDateFormat("HH:MM")
        sdf.timeZone = TimeZone.getDefault();
        return sdf.format(date)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                requestLocationData()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }

    }

}