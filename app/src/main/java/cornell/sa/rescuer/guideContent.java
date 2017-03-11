package cornell.sa.rescuer;

import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            InputStream is = getResources().openRawResource(R.raw.active_shooter);
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
