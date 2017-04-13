package cornell.sa.rescuer;

import android.support.design.widget.FloatingActionButton;
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
        View myView = this.getView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.activity_action, container, false);
        //POLICE CALL FUNCTION
        FloatingActionButton police = (FloatingActionButton)myView.findViewById(R.id.police);
        police.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).policeClick(v);
            }
        });
        //TAXICALL
        FloatingActionButton taxi = (FloatingActionButton)myView.findViewById(R.id.taxi);
        taxi.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).callTaxi(v);
            }
        });
        //DIRECTION
        FloatingActionButton home = (FloatingActionButton)myView.findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                ((MainActivity)getActivity()).takeDirection(v);
            }
        });
        //CALL
        FloatingActionButton call = (FloatingActionButton)myView.findViewById(R.id.contact);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).callClicked(view);
            }
        });
        return myView;
    }

}
