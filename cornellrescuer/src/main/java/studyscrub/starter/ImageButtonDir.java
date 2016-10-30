package studyscrub.starter;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.wearable.view.CircularButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.Node;

/**
 * Created by yiweini on 8/10/16.
 */
public class ImageButtonDir extends Fragment {

    private View buttonView;
    private Node mNode;
    private GoogleApiClient mGoogleApiClient;

    public ImageButtonDir(){
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
                String dir = "http://maps.google.com/maps?&daddr=";
                dir = dir.concat("105 Highland Place, Ithaca");
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(dir));
                startActivity(map);
            }
        });
        return buttonView;
    }
}
