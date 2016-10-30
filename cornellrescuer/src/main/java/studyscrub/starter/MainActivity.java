package studyscrub.starter;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.view.GridViewPager;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.android.gms.wearable.Wearable;

public class MainActivity extends Activity {

    private Node mNode;
    private boolean connectionOK = true;
    private GoogleApiClient mGoogleApiClient;
    private TwoDPicker tdp;
    private GridViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pager = (GridViewPager) this.findViewById(R.id.container);
        tdp = new TwoDPicker(this, getFragmentManager());
        pager.setAdapter(tdp);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.CALL_PHONE},
                0);

        //google api initialization
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle connectionHint){
                        // Now you can use the Data Layer API
                        Log.d("googleApi", "onConnected: " + connectionHint);
                        findNode();
                    }
                    @Override
                    public void onConnectionSuspended(int cause) {
                        Log.d("googleApi", "onConnectionSuspended: " + cause);
                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult result) {
                        Log.d("googleApi", "onConnectionFailed: " + result);
                        connectionOK = false;
                    }
                })
                // Request access only to the Wearable API
                .addApi(Wearable.API)
                .build();
    }

    @Override
    protected void onStart(){
        super.onStart();
        if (connectionOK){
            mGoogleApiClient.connect();
        }
    }

    private void findNode(){
        Wearable.NodeApi.getConnectedNodes(mGoogleApiClient).setResultCallback(new ResultCallback<NodeApi.GetConnectedNodesResult>() {
            @Override
            public void onResult(@NonNull NodeApi.GetConnectedNodesResult nodesResult) {
                for (Node node : nodesResult.getNodes()){
                    if (node.isNearby()) {
                        mNode = node;
                        tdp.setParam(mNode, mGoogleApiClient);
                        return;
                    }
                    mNode = node;
                    tdp.setParam(mNode, mGoogleApiClient);
                }
            }
        });
    }
}
