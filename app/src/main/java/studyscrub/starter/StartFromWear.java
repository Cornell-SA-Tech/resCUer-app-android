package studyscrub.starter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by yiweini on 8/1/16.
 */
public class StartFromWear extends WearableListenerService {
    private static final String START_BLUE_LIGHT = "/callBlue";

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        if (messageEvent.getPath().equals(START_BLUE_LIGHT)){
            SharedPreferences sharedPref = this.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
            String num = "tel:";
            num = num.concat(sharedPref.getString("FRIEND", "6277777777"));
            Intent callBlue = new Intent(Intent.ACTION_CALL);
            callBlue.setData(Uri.parse(num));
            callBlue.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PackageManager pm = this.getPackageManager();
            int hasPerm = pm.checkPermission(
                    android.Manifest.permission.CALL_PHONE,
                    this.getPackageName());
            if (hasPerm == PackageManager.PERMISSION_GRANTED) {
                startActivity(callBlue);
            }
            else{
                callBlue = new Intent(Intent.ACTION_DIAL);
                callBlue.setData(Uri.parse(num));
                startActivity(callBlue);
            }
        }
    }
}