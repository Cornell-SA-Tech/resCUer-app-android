package cornell.sa.rescuer;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by yiweini on 3/9/17.
 */
public class actions extends Fragment {

    public void actions(){}

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate((savedInstanceState));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_action, container, false);
    }

    public void call(View view){
        SharedPreferences sharedPref = getActivity().getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String num = "tel:";
        String retrieve = sharedPref.getString("FRIEND", "NON");
        num = num.concat(retrieve);
        if (retrieve == "NON"){
            Toast t = Toast.makeText(getActivity(), "Please setup the phone number first", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse(num));
        PackageManager pm = getActivity().getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                getActivity().getPackageName());
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
        PackageManager pm = getActivity().getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                getActivity().getPackageName());
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
        PackageManager pm = getActivity().getPackageManager();
        int hasPerm = pm.checkPermission(
                android.Manifest.permission.CALL_PHONE,
                getActivity().getPackageName());
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
        SharedPreferences sharedPref = getActivity().getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String home = sharedPref.getString("HOME", "NON");
        if (home == "NON"){
            Toast t = Toast.makeText(getActivity(), "Please setup your home address first", Toast.LENGTH_LONG);
            t.show();
            return;
        }
        dir = dir.concat(home);
        Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(dir));
        startActivity(map);
    }
}
