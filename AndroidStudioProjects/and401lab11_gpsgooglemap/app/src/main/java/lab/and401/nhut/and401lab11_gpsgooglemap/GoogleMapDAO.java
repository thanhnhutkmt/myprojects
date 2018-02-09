package lab.and401.nhut.and401lab11_gpsgooglemap;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nhut on 6/30/2017.
 */

public class GoogleMapDAO extends AsyncTask<Object, String, String> {
    private String loadDataUrl(String strUrl) throws IOException {
        String data = "";
        BufferedReader br = null;
        HttpURLConnection con = null;
        StringBuilder sb = new StringBuilder();
        try {
            con = (HttpURLConnection) new URL(strUrl).openConnection();
            br = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String temp;
            while((temp = br.readLine()) != null) sb.append(temp);
            br.close();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) br.close();
            if (con != null) con.disconnect();
        }
        return sb.toString();
    }

    private List<HashMap<String, String>> parse(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getPlaces(jsonArray);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray) {
        int placesCount = jsonArray.length();
        List<HashMap<String, String>> placesList = new ArrayList<>();
        HashMap<String, String> placeMap = null;
        for(int i = 0; i < placesCount; i++) {
            try {
                placeMap = getPlace((JSONObject)jsonArray.get(i));
                placesList.add(placeMap);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }
        return placesList;
    }

    private HashMap<String, String> getPlace(JSONObject googlePlaceJson) {
        HashMap<String, String> googlePlaceMap = new HashMap<String, String>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longtitude = "";
        String reference = "";
        try {
            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
            }
            if (!googlePlaceJson.isNull("vicinity")) {
                vicinity = googlePlaceJson.getString("vicinity");
            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longtitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference = googlePlaceJson.getString("reference");
            googlePlaceMap.put("place_name", placeName);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longtitude);
            googlePlaceMap.put("reference", reference);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return googlePlaceMap;
    }

    String googlePlacesData;
    GoogleMap mMap;
    String url;

    @Override
    protected void onPostExecute(String s) {
        if (s != null && s.length() > 0) showNearbyPlaces(new GoogleMapDAO().parse(s));
    }

    @Override
    protected String doInBackground(Object... params) {
        try {
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            googlePlacesData = new GoogleMapDAO().loadDataUrl(url);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return googlePlacesData;
    }

    private void showNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            LatLng position = new LatLng(Double.parseDouble(googlePlace.get("lat")),
                                         Double.parseDouble(googlePlace.get("lng")));
            MarkerOptions mo = new MarkerOptions();
            mo.title(googlePlace.get("place_name" + " : " + googlePlace.get("vicinity")));
            mo.position(position);
            mo.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(mo);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(position));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        }
    }

}

