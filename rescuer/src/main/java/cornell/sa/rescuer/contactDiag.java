package cornell.sa.rescuer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by yiweini on 3/24/17.
 */
public class contactDiag extends DialogFragment {
    final MainActivity paren = (MainActivity)getActivity();
    private CharSequence[] phones;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        SharedPreferences sharedPref = paren.getSharedPreferences("SAVED_FILE", Context.MODE_PRIVATE);
        builder.setTitle(R.string.pickcontact);
        int numContact = sharedPref.getInt("NUM", -1);
        for (int i = 0; i < numContact; i++){
            phones[i] = sharedPref.getString("NUM"+i, "NON");
        }

        builder.setItems(phones, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                paren.call(which);
                return;
            }
        });
        return builder.create();
    }
}
