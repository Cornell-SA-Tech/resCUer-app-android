package com.example.cornellrescuer;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.wearable.activity.ConfirmationActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.support.wearable.view.CircledImageView;
import android.support.wearable.view.CircularButton;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.Inflater;

public class MainActivity extends FragmentGridPagerAdapter{

    public static class ImageButtonFrag extends Fragment{

        private View buttonView;

        public ImageButtonFrag(){
            super();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup containers, Bundle SavedInstances){
            buttonView = inflater.inflate(R.layout.button_fragment, containers, false);
            CircularButton cb = (CircularButton) buttonView.findViewById(R.id.call_police);
            cb.setImageResource(R.drawable.black_circle);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), "yeah", Toast.LENGTH_LONG);
                }
            });
            return buttonView;
        }

    }

    private Fragment[][] buttons;

    public MainActivity(Context ctx, FragmentManager fm){
        super(fm);
        buttons = new Fragment[1][4];
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Fragment res = new ImageButtonFrag();
        return res;
    }

    @Override
    public int getRowCount() {
        return 4;
    }

    @Override
    public int getColumnCount(int i) {
        return 1;
    }
}
