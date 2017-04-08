package cornell.sa.rescuer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by yiweini on 3/10/17.
 */
public class guide extends ListFragment {
    public void guide(){}
    private static int GUIDE = 1;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String[] values = new String[] {  "Active shooter",
        "Animal incidents",
        "Bomb threat",
        "Building evacuation",
        "Crime",
        "Earthquake",
        "Elevator emergency",
        "Facility problem",
        "Fire explosion",
        "Hazardous",
        "Health emergency",
        "Severe weather",
        "Suspicious mail",
        "Workplace violence",
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.guide_view,values);
        setListAdapter(adapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        ListView lview = getListView();
        lview.setDivider(new ColorDrawable(Color.GRAY));
        getListView().setDividerHeight(1);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getActivity(), guideContent.class);
        intent.putExtra("id", position);
        startActivity(intent);
    }


}
