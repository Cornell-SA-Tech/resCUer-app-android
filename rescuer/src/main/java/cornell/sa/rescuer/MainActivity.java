package cornell.sa.rescuer;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener{

    //Class variables
    private Location curLocation;
    private pageAdapter adapter;
    public final String[] EMERGENCY = {"911", "607-255-1111", "607-274-4011"};
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        if (this.findViewById(R.id.main_layout) != null) {
//            this.findViewById(R.id.main_layout).setBackgroundColor(0xFF5a6370);
//            this.findViewById(R.id.main_layout).invalidate();
//        }
        // Create an instance of GoogleAPIClient.
        // Create the LocationRequest object

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1 * 500)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 500); // 1 second, in milliseconds

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.setBackgroundColor(0xFF46505e);
        tabLayout.setBackgroundColor(0xFFE57373);
        tabLayout.setTabTextColors(0xFFFFFFFF, 0xFFFFFFFF);

        SharedPreferences sharedPref = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String home = sharedPref.getString("HOME", "NON");
        String retrieve = sharedPref.getString("FRIEND", "NON");
        if (home == "NON" || retrieve == "NON"){
            Toast.makeText(this, "Please setup your information in settings", Toast.LENGTH_LONG).show();
        }

        adapter = new pageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onResume(){
        super.onResume();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle){
        try{
            curLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (curLocation == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            }
        }catch(SecurityException e){
            Toast.makeText(this, "Unable to access location information", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,9000);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            Log.i("failed", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    public Location getCurLocation(){
        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return curLocation;
    }

    @Override
    public void onLocationChanged(Location location) {
        curLocation = location;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.setting:
                Intent intent = new Intent(this, setting.class);
                startActivity(intent);
                break;
            default:
                break;
        }

        return true;
    }

    public void callClicked(View view){
        ContactDiag cd = new ContactDiag();
        cd.show(getFragmentManager(), "CD");
    }

    public void call(int which){
        SharedPreferences sharedPref = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String num = "tel:";
        String retrieve = sharedPref.getString("PNUM"+Integer.toString(which+1), "NON");
        num = num.concat(retrieve);
        if (retrieve == "NON"){
            Toast t = Toast.makeText(this, "Please setup the phone number first", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                getPackageName());
        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
        else{
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(num));
            startActivity(intent);
        }
    }

    public void callTaxi(View view){
        String num = "tel:6072777777";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                getPackageName());
        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
        else{
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(num));
            startActivity(intent);
        }
    }

    public void policeClick(View view){
        PoliceDiag pd = new PoliceDiag();
        pd.show(getFragmentManager(), "Police dialog");
    }

    public void callPolice(int which){
        String num = "tel:";
        num += EMERGENCY[which];
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
               getPackageName());
        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
        else{
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(num));
            startActivity(intent);
        }
    }

    public void takeDirection(View view){
        String dir = "http://maps.google.com/maps?&daddr=";
        SharedPreferences sharedPref = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String home = sharedPref.getString("STREET", "NON")+",";
        home += sharedPref.getString("CITY","");
        if (home == "NON"){
            Toast t = Toast.makeText(this, "Please setup your home address first", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        dir = dir.concat(home);
        Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(dir));
        startActivity(map);
    }
}
