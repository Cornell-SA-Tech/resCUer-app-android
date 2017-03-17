package cornell.sa.rescuer;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by yiweini on 3/10/17.
 */
public class guideContent extends AppCompatActivity {
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent father = getIntent();
        id = father.getIntExtra("id", 0);
        setContentView(R.layout.guide_content);
        ArrayList<instruction> insts = fromJson();
        contentAdapter ca = new contentAdapter(this, insts);
        ListView lv = (ListView) findViewById(R.id.contentList);
        lv.setAdapter(ca);
    }

    public ArrayList<instruction> fromJson(){
        ArrayList<instruction> instructions = new ArrayList<>();
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset());
            JSONArray ins = json.getJSONArray("instruction");
            for (int i =0; i < ins.length(); i++){
                JSONObject j = ins.getJSONObject(i);
                instructions.add(new instruction(j.getString("title"), j.getString("text")));
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return instructions;
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is;
            switch(id){
                case 0:
                    is = getResources().openRawResource(R.raw.active_shooter);
                    break;
                case 1:
                    is = getResources().openRawResource(R.raw.animal_incident);
                    break;
                case 2:
                    is = getResources().openRawResource(R.raw.bomb_threat);
                    break;
                case 3:
                    is = getResources().openRawResource(R.raw.building_evacuation);
                    break;
                case 4:
                    is = getResources().openRawResource(R.raw.crime);
                    break;
                case 5:
                    is = getResources().openRawResource(R.raw.earthquake);
                    break;
                case 6:
                    is = getResources().openRawResource(R.raw.elevator_emergency);
                    break;
                case 7:
                    is = getResources().openRawResource(R.raw.facility_problem);
                    break;
                case 8:
                    is = getResources().openRawResource(R.raw.fire_explosion);
                    break;
                case 9:
                    is = getResources().openRawResource(R.raw.hazardous);
                    break;
                case 10:
                    is = getResources().openRawResource(R.raw.health_emergency);
                    break;
                case 11:
                    is = getResources().openRawResource(R.raw.severe_weather);
                    break;
                case 12:
                    is = getResources().openRawResource(R.raw.suspicious_mail);
                    break;
                case 13:
                    is = getResources().openRawResource(R.raw.workplace_violence);
                    break;
                default:
                    is = getResources().openRawResource(R.raw.active_shooter);
                    break;
            }
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(),"json got nothing", Toast.LENGTH_LONG).show();
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
