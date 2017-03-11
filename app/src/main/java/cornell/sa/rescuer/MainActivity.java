package cornell.sa.rescuer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (this.findViewById(R.id.main_layout) != null) {
            this.findViewById(R.id.main_layout).setBackgroundColor(0xFF323D4D);
            this.findViewById(R.id.main_layout).invalidate();
        }

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setBackgroundColor(0xFF46505e);
        tabLayout.setTabTextColors(0xFFd6d8db, 0xFFd6d8db);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final pageAdapter adapter = new pageAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        SharedPreferences sharedPref = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String home = sharedPref.getString("HOME", "NON");
        String retrieve = sharedPref.getString("FRIEND", "NON");
        if (home == "NON" || retrieve == "NON"){
            Toast.makeText(this, "Please setup your information in settings", Toast.LENGTH_LONG).show();
        }
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

    public void call(View view){
        SharedPreferences sharedPref = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
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

    public void callPolice(View view){
        String num = "tel:2551111";
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
