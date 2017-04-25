package cornell.sa.rescuer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class setting extends AppCompatActivity {
    private HashMap<Integer,Integer> id2res = new HashMap<Integer, Integer>();
    private HashMap<Integer,Integer> res2id = new HashMap<Integer, Integer>();
    private static int MAX_NUM = 5;
    private SharedPreferences sf;
    private ViewGroup main;
    private ArrayList<Card> contacts = new ArrayList(11);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        //change colors
//        if (this.findViewById(R.id.setting) != null) {
//            this.findViewById(R.id.setting).setBackgroundColor(0xFF323D4D);
//            this.findViewById(R.id.setting).invalidate();
//        }

        //initiate hashmap
        for (int j = 1; j <= 10; j++){
            String identfy = "card_"+Integer.toString(j);
            id2res.put(j, getResources().getIdentifier(identfy, "id", getPackageName()));
            res2id.put(getResources().getIdentifier(identfy, "id", getPackageName()), j);
        }
        contacts.add(null);

        //add contact cards
        sf = getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        main = (ViewGroup)findViewById(R.id.setting);
        int numPhone = sf.getInt("NUM", 0);
        for (int i = 1; i <= numPhone; i++){
            appendContact(i);
        }
        //fill home card
        refreshHome();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void appendContact(int i){
        String name = sf.getString("NICK"+Integer.toString(i), "Contact"+Integer.toString(i));
        String numb = sf.getString("PNUM"+Integer.toString(i), "null");
        String identfy = "card_"+Integer.toString(i);
        String card_identfy = "Card_"+Integer.toString(i);
        int id = getResources().getIdentifier(identfy, "id", getPackageName());
        Card c1 = new Card(this.getApplicationContext(), name, numb, id);
        c1.setId(getResources().getIdentifier(card_identfy,"id",getPackageName()));
        c1.getEdit().setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                editContact(v);
            }
        });
        c1.getDelete().setOnClickListener(new TextView.OnClickListener(){
            public void onClick(View v){
                delContact(v);
            }
        });
        contacts.add(c1);
        main.addView(c1);
    }

    public void addContact(View view){
        int curID = sf.getInt("NUM", 0);
        if (curID == MAX_NUM){
            Toast toast = Toast.makeText(this, "Maximum number of contacts", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        AddContactDiag acd = new AddContactDiag();
        acd.show(getFragmentManager(), "addContact");
    }

    public void editContact(View view){
        //start dialog
        EditContactDiag ecd = new EditContactDiag();

        LinearLayout control = (LinearLayout)view.getParent();
        int resID = control.getId();
        Bundle args = new Bundle();
        args.putInt("ID", res2id.get(resID));
        ecd.setArguments(args);
        ecd.show(getFragmentManager(),"editContact");
    }
    public void updateContact(int id){
        String name = sf.getString("NICK"+Integer.toString(id), "Contact"+Integer.toString(id));
        String numb = sf.getString("PNUM"+Integer.toString(id), "null");
        Card temp = contacts.get(id);
        temp.update(name, numb);
        contacts.set(id, temp);
    }

    public int idToRes(int resID){
        return res2id.get(resID);
    }
    public int resToId(int id){
        return res2id.get(id);
    }

    public void delContact(View view){
        final View button = view;
        AlertDialog.Builder ab = new AlertDialog.Builder(this);
        ab.setTitle("Delete quick contact");
        ab.setMessage("Are you sure you want to delete this contact?");
        ab.setPositiveButton(R.string.del, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                delCard(button);
            }
        });

        ab.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        final AlertDialog ad = ab.create();
        ad.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                ad.getButton(android.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                ad.getButton(android.app.AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        ad.show();
    }
    public void delCard(View view){
        LinearLayout control = (LinearLayout)view.getParent();
        int resID = control.getId();
        int id = res2id.get(resID);
        String ident = "Card_"+Integer.toString(id);
        View v = findViewById(getResources().getIdentifier(ident, "id", getPackageName()));
        ((ViewGroup)v.getParent()).removeView(v);
        main.invalidate();
        int nums = sf.getInt("NUM",-1);
        sf.edit().putInt("NUM", nums-1).commit();
        for (int i = id; i < nums; i++){
            String nickPrev = "NICK"+Integer.toString(i);
            String nickAfter = "NICK"+Integer.toString(i+1);
            sf.edit().putString(nickPrev, sf.getString(nickAfter,"")).commit();
            String numPrev = "PNUM"+Integer.toString(i);
            String numAfter = "PNUM"+Integer.toString(i+1);
            sf.edit().putString(numPrev, sf.getString(numAfter,"")).commit();
        }
    }

    public void editHome(View view){
        EditHomeDiag ed = new EditHomeDiag();
        ed.show(getFragmentManager(), "Edit home");
    }


    public void refreshHome(){
        String street = sf.getString("STREET", "Not set yet.");
        String city = sf.getString("CITY", "");
        String homeAddr = street+"\n"+city;
        TextView home = (TextView)findViewById(R.id.hometext);
        home.setText(homeAddr);
    }

}
