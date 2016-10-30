package studyscrub.starter;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.CircularButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

/**
 * Created by yiweini on 7/20/16.
 */
public class ImageButtonCall extends Fragment{

    private View buttonView;
    private Node mNode;
    private GoogleApiClient mGoogleApiClient;

    public ImageButtonCall(){
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containers, Bundle SavedInstances){
        buttonView = inflater.inflate(R.layout.button_fragment, containers, false);
        CircularButton cb = (CircularButton) buttonView.findViewById(R.id.call_police);
        cb.setColor(R.color.black);
        cb.setImageResource(R.drawable.black_circle);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent direct = new Intent(Intent.ACTION_CALL);
                    direct.setData(Uri.parse("tel:5073124600"));
                    startActivity(direct);
                }
                catch(Throwable e){
                    Toast phone = new Toast(getContext());
                    phone.setText("starting on phone");
                    phone.show();
                    if (mNode == null){
                        Log.d("nodeibf", "node is null");
                    }
                    if (mGoogleApiClient == null){
                        Log.d("mgoogleibf", "mgoogle is null");
                    }
                    if(!mGoogleApiClient.isConnected()){
                        Log.d("mgoogle", "mgoogle is not connected");
                    }
                    if(mNode != null && mGoogleApiClient != null && mGoogleApiClient.isConnected()){
                        Wearable.MessageApi.sendMessage(mGoogleApiClient, mNode.getId(), "/callBlue", null).setResultCallback(
                                new ResultCallback<MessageApi.SendMessageResult>() {
                                    @Override
                                    public void onResult(MessageApi.SendMessageResult sendMessageResult) {
                                        if (!sendMessageResult.getStatus().isSuccess()) {
                                            Log.e("TAG", "Failed to send message with status code: "
                                                    + sendMessageResult.getStatus().getStatusCode());
                                        }
                                    }
                                }
                        );
                    }
                }
            }
        });
        return buttonView;
    }

    public void setParameter(GoogleApiClient gac, Node n){
        Log.d("node", "setNodeinIBF");
        if (n == null){
            Log.d("node", "tricked");
        }
        mNode = n;
        mGoogleApiClient = gac;
    }

    public void setLabel(String label){
        TextView tv = buttonView.findViewById();
    }

}
