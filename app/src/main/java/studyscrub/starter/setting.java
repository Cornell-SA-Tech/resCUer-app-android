package studyscrub.starter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        if (this.findViewById(R.id.setting) != null) {
            this.findViewById(R.id.setting).setBackgroundColor(0xFF323D4D);
            this.findViewById(R.id.setting).invalidate();
        }

        this.findViewById(R.id.edit_home).getBackground().mutate().setColorFilter(getResources().
                getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        this.findViewById(R.id.edit_phone).getBackground().mutate().setColorFilter(getResources().
                getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

        EditText ph = (EditText)findViewById(R.id.edit_phone);
        ph.setHint("Current friend's number is:\n" + getPhone());

        EditText ha = (EditText)findViewById(R.id.edit_home);
        ha.setHint("Current address is:\n" + getHome());

        ph.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keycode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keycode == KeyEvent.KEYCODE_ENTER)){
                    setPhone(v);
                    return true;
                }
                return false;
            }
        });

        ha.setOnKeyListener(new View.OnKeyListener(){
            public boolean onKey(View v, int keycode, KeyEvent event){
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keycode == KeyEvent.KEYCODE_ENTER)){
                    setPhone(v);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MyActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public String getHome(){
        SharedPreferences sharedPref = this.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String res = sharedPref.getString("HOME", "Not set yet");
        return res;
    }

    public String getPhone(){
        SharedPreferences sharedPref = this.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        String res = sharedPref.getString("FRIEND", "Not set yet");
        return res;
    }

    public void setPhone(View view){
        SharedPreferences.Editor sharedPref = this.getSharedPreferences
                ("SAVED_FILE", Context.MODE_PRIVATE).edit();
        EditText editText = (EditText) findViewById(R.id.edit_phone);
        String phone_num = editText.getText().toString();
        if(phone_num.matches("[0-9]+")&& (phone_num.length() == 7 || phone_num.length() == 10)){
            sharedPref.putString("FRIEND", phone_num).commit();
            Toast toast = Toast.makeText(this, "Phone Number Saved", Toast.LENGTH_SHORT);
            EditText ph = (EditText)findViewById(R.id.edit_phone);
            ph.setText("Current friend's number is:\n" + getPhone());
            toast.show();
            return;
        }
        else{
            Toast toast = Toast.makeText(this, "Invalidate phone number", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
    }

    public void setHome(View view){
        SharedPreferences.Editor sharedPref = this.getSharedPreferences
                ("SAVED_FILE", Context.MODE_PRIVATE).edit();
        EditText editText = (EditText) findViewById(R.id.edit_home);
        String home_addr = editText.getText().toString();
        sharedPref.putString("HOME", home_addr).commit();
        Toast toast = Toast.makeText(this, "Home Address Saved", Toast.LENGTH_SHORT);
        LinearLayout layout = (LinearLayout) findViewById(R.id.setting);
        EditText ha = (EditText)findViewById(R.id.edit_home);
        ha.setText("Current address is:\n" + getHome());
        toast.show();
        return;
    }
}
