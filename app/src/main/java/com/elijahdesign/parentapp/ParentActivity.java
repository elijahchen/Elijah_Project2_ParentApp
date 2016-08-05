package com.elijahdesign.parentapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentActivity extends AppCompatActivity implements LocationListener {

    EditText userNameText;
    EditText radiusText;
    EditText longitudeText;
    EditText latitudeText;

    private static final int LOCATION_PERMISSION_REQUEST = 1;

    Button createButton;
    Button checkStatusButton;
    Button updateButton;
    Button refreshButton;

    JSONData jsonClient;
    Location location;
    LocationManager locationManager;

    boolean isNetworkEnabled;
    boolean isGPSEnabled;

    String child_latitude;
    String child_longitude;

    String userID;
    String radius;
    String longitude;
    String latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        userNameText = (EditText) findViewById(R.id.username_EditText);
        radiusText = (EditText) findViewById(R.id.radius_EditText);
        longitudeText = (EditText) findViewById(R.id.longitude_EditText);
        latitudeText = (EditText) findViewById(R.id.latitude_EditText);

        createButton = (Button) findViewById(R.id.createButton_xml);
        checkStatusButton = (Button) findViewById(R.id.checkStatusButton_xml);
        updateButton = (Button) findViewById(R.id.updateButton_xml);
        refreshButton = (Button) findViewById(R.id.refresh_Button);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createId();
            }
        });

        checkStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getStatus();
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLoc();
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocation();
            }
        });



        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST);
        } else {
            getLocation();
        }
    }

    /*
    METHODS
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case LOCATION_PERMISSION_REQUEST:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    // You don't have it, react accordingly
                }
        }
    }

    public void createId() {

        userID = userNameText.getText().toString();
        radius = radiusText.getText().toString();
        longitude = longitudeText.getText().toString();
        latitude = latitudeText.getText().toString();

        prepClientData(userID, radius, longitude, latitude);

    }

    public void prepClientData(String userID, String radius, String longitude, String latitude) {

        ClientData clientData = new ClientData(
                userID,
                radius,
                longitude,
                latitude
        );

        jsonClient = new RestClient().getApiService();
        Call call = jsonClient.putClient(clientData, userID + ".json");

        call.enqueue(new Callback<ClientData>() {
            @Override
            public void onResponse(Call<ClientData> call, Response<ClientData> response) {

                //TODO: Try to read response

                Toast.makeText(getApplicationContext(), "Successfully posted to server", Toast.LENGTH_SHORT).show();
                Log.i("Success", "" + response.body().userID);
            }

            @Override
            public void onFailure(Call<ClientData> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Failed to post to server", Toast.LENGTH_SHORT).show();
                Log.i("Failed", "" + t);
            }
        });
    }

    private void getLocation() {
        //Do location stuff
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if(isGPSEnabled){
            if(locationManager != null){
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }

        } else if (isNetworkEnabled){
            if(locationManager != null){
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, this);
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }

        getCurrentLocation();
    }

    public void getCurrentLocation(){
        if(location != null){
            longitude = Double.toString(location.getLongitude());
            latitude = Double.toString(location.getLatitude());
            longitudeText.setText(longitude);
            latitudeText.setText(latitude);
        }
    }

    public void updateLoc() {
        //TODO: Maybe just update Radius
        //TODO: Check for existing userID, change below accordingly

//        userID = userNameText.getText().toString();
//        radius = radiusText.getText().toString();
//        longitude = longitudeText.getText().toString();
//        latitude = latitudeText.getText().toString();
//
//        prepClientData(userID, radius, longitude, latitude);
    }

    public boolean getStatus() {

        //TODO: Send HTTP GET request to /digitalleash/<userid>.json
        //TODO: Convert JSON to JSONObject, extract value and compute child is in zone?
        //TODO: COMPUTE IF IN ZONE, two locationsa, Location.distanceTo
        //TODO: Now if radius is less than equal then child is in zone

        //TODO: Fire fail fragment
        //TODO: Else fire success fragment

        return true;
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
