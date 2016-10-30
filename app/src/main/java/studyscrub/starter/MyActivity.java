package studyscrub.starter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Wearable;

import java.util.List;

public class MyActivity extends AppCompatActivity {
    private GoogleApiClient mGAC;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        if (this.findViewById(R.id.back) != null) {
            this.findViewById(R.id.back).setBackgroundColor(0xFF323D4D);
            this.findViewById(R.id.back).invalidate();
        }
        mGAC = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint){
                        // Now you can use the Data Layer API
                        Log.d("googleApi", "onConnected: " + connectionHint);
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d("googleApi", "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("googleApi", "onConnectionFailed: " + result);
                    }
                })
                // Request access only to the Wearable API
                .addApi(Wearable.API)
                .build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
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


    public void call(View view){
        SharedPreferences sharedPref = this.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String num = "tel:";
        String retrieve = sharedPref.getString("FRIEND", "NON");
        num = num.concat(retrieve);
        if (retrieve == "NON"){
            Toast t = Toast.makeText(this, "Please setup the phone number first", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = this.getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                this.getPackageName());
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
        PackageManager pm = this.getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                this.getPackageName());
        if (hasPerm == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        }
        else{
            intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse(num));
            startActivity(intent);
        }
    }

    public void callPolice(View view){
        String num = "tel:2551111";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = this.getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                this.getPackageName());
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
        SharedPreferences sharedPref = this.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String home = sharedPref.getString("HOME", "NON");
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
