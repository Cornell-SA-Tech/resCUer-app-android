package cornell.sa.rescuer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by yiweini on 3/24/17.
 */
public class ContactDiag extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final MainActivity paren = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        SharedPreferences sharedPref = paren.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        builder.setTitle(R.string.pickcontact);
        int total = sharedPref.getInt("NUM", 0);
        String nick = "";
        for (int i = 1; i <= total; i++){
            nick += sharedPref.getString("NICK"+Integer.toString(i),"")+"`";
        }
        String[] nicks = nick.split("`");

        builder.setItems(nicks, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                paren.call(which);
                return;
            }
        });
        return builder.create();
    }
}
