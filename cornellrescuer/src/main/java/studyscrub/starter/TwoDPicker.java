package studyscrub.starter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.wearable.view.FragmentGridPagerAdapter;
import android.util.Log;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;

public class TwoDPicker extends FragmentGridPagerAdapter{

    private ImageButtonCall[][] buttons;
    private String[] labelName;
    private Node mNode;
    private GoogleApiClient mGoogleApiClient;

    public TwoDPicker(Context ctx, FragmentManager fm){
        super(fm);
        buttons = new ImageButtonCall[1][4];
        labelName = new String[4];
        labelName[0] = "call police";
        for (int i = 0; i < 4; i++){
            buttons[0][i] = new ImageButtonCall();
        }
    }

    @Override
    public Fragment getFragment(int row, int col) {
        buttons[row][col].setParameter(mGoogleApiClient, mNode);
        buttons[row][col].setLabel(labelName[col]);
        return(buttons[row][col]);
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int i) {
        return 4;
    }

    public void setParam(Node n, GoogleApiClient gac){
        Log.d("node", "setNodein2d");
        mNode = n;
        mGoogleApiClient = gac;
        if (mNode == null){
            Log.d("node", "node2d is null");
        }
        if (mGoogleApiClient == null){
            Log.d("mgoogle", "mgoogle is null");
        }
        if(!mGoogleApiClient.isConnected()){
            Log.d("mgoogle", "mgoogle is not connected");
        }
        for (ImageButtonCall ibf : buttons[0]){
            ibf.setParameter(mGoogleApiClient, mNode);
        }
    }
}
