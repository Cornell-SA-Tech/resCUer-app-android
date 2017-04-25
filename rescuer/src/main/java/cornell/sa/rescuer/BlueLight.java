package cornell.sa.rescuer;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chenn on 4/23/2017.
 */

public class BlueLight extends SupportMapFragment{
    private Activity activity;
    private GoogleMap map;

    public BlueLight(){
        super();
        newInstance();
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        this.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Location location = ((MainActivity)getActivity()).getCurLocation();

                try{
                    googleMap.setMyLocationEnabled(true);
                }catch(SecurityException e){
                    Toast.makeText(getActivity(),"please enable location service",Toast.LENGTH_LONG).show();
                }

                location = ((MainActivity)getActivity()).getCurLocation();
                if(location == null) {
                    location = new Location("");
                    location.setLatitude(42.448795d);
                    location.setLongitude(-76.483939d);
                }
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 14));

                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                        .zoom(17)                   // Sets the zoom
                        .bearing(90)                // Sets the orientation of the camera to east
                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                //add markers
                addMarkers(googleMap);
            }
        });
    }

    public void addMarkers(GoogleMap gm){
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray ms = json.getJSONArray("marks");
            for (int i =0; i < ms.length(); i++){
                JSONObject m = ms.getJSONObject(i);
                gm.addMarker(new MarkerOptions()
                        .position(new LatLng(m.getDouble("Y"), m.getDouble("X")))
                        .title(m.getString("Address"))
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is;
            is = getResources().openRawResource(R.raw.bluelight);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Toast.makeText(getActivity(),"json got nothing", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
