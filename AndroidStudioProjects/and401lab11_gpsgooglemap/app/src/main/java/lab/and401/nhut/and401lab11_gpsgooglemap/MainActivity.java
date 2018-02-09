package lab.and401.nhut.and401lab11_gpsgooglemap;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private TextView tv_geocoordinate;
    private GoogleApiClient mGoogleApiClient;
    private ImageView ivRadar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_geocoordinate = (TextView) findViewById(R.id.tv_geocoordinate);
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.googlemap);
        mapFragment.getMapAsync(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addApi(Places.GEO_DATA_API)
            .addApi(Places.PLACE_DETECTION_API)
            .enableAutoManage(this, this)
            .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (permissions[0]) {
            case Manifest.permission.ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    Snackbar.make(tv_geocoordinate,
                        "Permission granted, now you can access location data.",
                        Snackbar.LENGTH_LONG).show();
                else
                    Snackbar.make(tv_geocoordinate,
                        "Permission denied, now you can not access location data.",
                        Snackbar.LENGTH_LONG).show();
                break;
        }
    }

    private boolean checkPersmiision() {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this, Manifest.permission.ACCESS_FINE_LOCATION))
            Toast.makeText(this, "GPS permission needed in order to capture your location"
                , Toast.LENGTH_SHORT).show();
        else
            ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
    }

    LocationListener locListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String street = getAddressFromLocation(location.getLongitude(), location.getLatitude());
            String loc = street.equals("") ? "error" :
                String.format("Location captured successsfully: longitude:%s latitude:%s"
                    , location.getLongitude(), location.getLatitude())
                    + "\nStreet: " + street;
            tv_geocoordinate.setText(loc);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider disabled", Toast.LENGTH_SHORT).show();
        }
    };

    private String getAddressFromLocation(double longitude, double latitude) {
        Geocoder geoCoder = new Geocoder(this);
        List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (list == null || list.isEmpty()) ? "" : list.get(0).getAddressLine(0).toString();
    }

    public void captureLocation(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            ((LocationManager) this.getSystemService(Context.LOCATION_SERVICE))
                    .requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(10.79, 106.68));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(10);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);
        getNearByRestaurant(10.79, 106.68, googleMap);
    }

    private void getNearByRestaurant(double lat, double lng, GoogleMap gMap) {
        gMap.clear();
        new GoogleMapDAO().execute(gMap, getUrl(lat, lng));
    }

    private String getUrl(double lat, double lng) {
        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?"
                + "location=" + lat + "," + lng
                + "&radius=" + 1500
                + "&type=restaurant"
                + "&sensor=true"
                + "&key=" + getString(R.string.google_maps_key);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Unable to find nearby restaurants, " +
                "check your internet connection", Toast.LENGTH_SHORT).show();
    }
}
