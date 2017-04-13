package cornell.sa.rescuer;

/**
 * Created by chenn on 4/12/2017.
 */

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import static android.R.attr.onClick;

/**
 * Created by chenn on 4/11/2017.
 */

public class AddContactDiag extends DialogFragment {
    @Override
    public AlertDialog onCreateDialog(Bundle savedInstanceState) {
        final setting paren = (setting)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(paren);
        LayoutInflater li = getActivity().getLayoutInflater();
        builder.setTitle("Add contact");
        View view = li.inflate(R.layout.diag_home, null);
        final EditText s_input = (EditText)view.findViewById(R.id.street);
        s_input.setHint("Nickname");
        final EditText c_input = (EditText)view.findViewById(R.id.city);
        c_input.setHint("Phone number");
        builder.setView(view);

        //save button
        builder.setPositiveButton(R.string.save,  new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sf = getActivity().getSharedPreferences("SAVED_FILE",
                        Context.MODE_PRIVATE);
                int cur = sf.getInt("NUM",0)+1;
                String nick = s_input.getText().toString();
                String number = c_input.getText().toString();
                sf.edit().putString("NICK"+Integer.toString(cur), nick).commit();
                sf.edit().putString("PNUM"+Integer.toString(cur), number).commit();
                sf.edit().putInt("NUM", cur).commit();
                Toast.makeText(paren, "Changes saved", Toast.LENGTH_SHORT).show();
                ((setting) getActivity()).appendContact(cur);
            }
        });
        //cancel button
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        //inflate edit text


        final AlertDialog result = builder.create();


        result.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                result.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
                result.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
            }
        });
        return result;
    }
}
