package cornell.sa.rescuer;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by yiweini on 3/10/17.
 */
public class instruction {
    public String Title;
    public String content;

    public instruction(String t, String c) {
        Title = t;
        content = c;
    }

}
